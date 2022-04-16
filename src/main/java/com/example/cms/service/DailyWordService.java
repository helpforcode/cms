package com.example.cms.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.example.cms.dto.DailyWordReq;
import com.example.cms.dto.HistoryReq;
import com.example.cms.storage.entity.DailyWord;
import com.example.cms.storage.entity.Word;
import com.example.cms.storage.repository.DailyWordRepository;
import com.example.cms.util.PageUtil;
import com.example.cms.vo.DailyWordVo;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DailyWordService {

    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private WordService wordService;

    @Autowired
    private DailyWordRepository repository;
    @Autowired
    private PageUtil pageUtil;

    private static final int publishHour = 21;

    private DailyWord latestPublished() {
        return repository.findFirstByStatusEqualsOrderByPublishedAtDesc(true);
    }
    private DailyWord nextToBePublish() {
        return repository.findFirstByStatusEqualsAndPublishedAtGreaterThanOrderByPublishedAtDesc(false, new Date());
    }
    public DailyWordVo latest() {
        DailyWord dailyWord = latestPublished();
        return getVo(dailyWord);
    }
    public DailyWordVo next() {
        DailyWord dailyWord = nextToBePublish();
        return getVo(dailyWord);
    }

    public DailyWordVo getVo(DailyWord dailyWord) {
        if (null == dailyWord) {
            return null;
        }
        String words = dailyWord.getWords();
        Set<Integer> wordIds = Arrays.stream(words.split(",")).map(Integer::valueOf).collect(Collectors.toSet());
        Integer primary = dailyWord.getWordPrimary();

        DailyWordVo vo = mapperFacade.map(dailyWord, DailyWordVo.class);

        Word primaryWord = wordService.find(primary);
        vo.setPrimaryWord(primaryWord);
        vo.setWords(wordService.words(wordIds));
        return vo;
    }

    public Page<DailyWordVo> list(Pageable pageable) {
        Page<DailyWord> page =  repository.findAll(pageable);
        List<DailyWord> dailyWords = page.getContent();
        List<DailyWordVo> vos = dailyWords.stream().map(this::getVo).collect(Collectors.toList());
        return pageUtil.listToPage(vos, page);
    }

    // todo: cache refresh when status up (schedule)
    @Cacheable(cacheNames = "dailyWords", key = "#year")
    public List<DailyWordVo> all(String year) {
        if (!StringUtils.hasLength(year)) {
            year = String.valueOf(DateUtil.year(new Date()));
        }
        return repository.findAllByDayStartsWithOrderByIdDesc(year).stream().map(this::getVo).collect(Collectors.toList());
    }

    public DailyWordVo getVo(Integer id) {
        return getVo(find(id));
    }
    public DailyWord find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void add(DailyWordReq req) throws Exception {
        if (!StringUtils.hasLength(req.getDay())) {
            throw new Exception("Date should be provided");
        }
        DailyWord model = new DailyWord();
        attachModel(model, req);
        DailyWord existed = repository.findFirstByPublishedAtAfter(new Date());
        if (existed != null) {
            throw new Exception("Existed " + existed.getDay());
        }
        if (null != repository.findFirstByDay(req.getDay())) {
            throw new Exception("Existed.");
        }
        DailyWord last = repository.findFirstByDayStartsWithOrderByCodeDesc(model.getDay().substring(0, 4));
        if (null != last) {
            model.setCode(last.getCode() + 1);
        } else {
            model.setCode(1);
        }
        repository.save(model);
    }

    public void del(Integer id) {
    }

    public void update(DailyWordReq req) {
        DailyWord model = repository.findById(req.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        attachModel(model, req);
        repository.save(model);
    }

    private void attachModel(DailyWord model, DailyWordReq req) {
        if (null != req.getWords()) {
            List<String> wordIds = req.getWords().stream().map(word -> String.valueOf(word.getId())).collect(Collectors.toList());
            model.setWords(String.join(",", wordIds));
        }
        if (null != req.getPrimaryWord()) {
            model.setWordPrimary(req.getPrimaryWord().getId());
        }
        if (null != req.getStatus()) {
            model.setStatus(req.getStatus());
        }
        if (StringUtils.hasLength(req.getDay())) {
            model.setDay(req.getDay());
            model.setPublishedAt(DateUtil.offsetHour(
                    DateUtil.parse(req.getDay(), DatePattern.NORM_DATE_PATTERN, DatePattern.NORM_DATETIME_PATTERN)
                    , publishHour));
        }
    }

}
