package com.example.cms.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.example.cms.dto.DailyWordDto;
import com.example.cms.dto.WordDto;
import com.example.cms.storage.entity.DailyWord;
import com.example.cms.storage.entity.Word;
import com.example.cms.storage.repository.DailyWordRepository;
import com.example.cms.util.PageUtil;
import com.example.cms.vo.DailyWordVo;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    private DailyWord latestPublished() {
        return repository.findFirstByStatusEqualsOrderByPublishedAtDesc(1);
    }
    public DailyWordVo latest() {
        DailyWord dailyWord = latestPublished();
        return getVo(dailyWord);
    }

    private DailyWordVo getVo(DailyWord dailyWord) {
        if (null == dailyWord) {
            return null;
        }
        String words = dailyWord.getWords();
        Set<Integer> wordIds = Arrays.stream(words.split(",")).map(Integer::valueOf).collect(Collectors.toSet());
        Integer primary = dailyWord.getWordPrimary();

        Word primaryWord = wordService.find(primary);
        DailyWordVo vo = new DailyWordVo();
        vo.setPrimaryWord(primaryWord);
        vo.setWords(wordService.words(wordIds));

        vo.setId(dailyWord.getId());
        vo.setStatus(dailyWord.getStatus());
        vo.setPublishedAt(dailyWord.getPublishedAt());
        vo.setDay(dailyWord.getDay());
        return vo;
    }

    public Page<DailyWordVo> list(Pageable pageable) {
        Page<DailyWord> page =  repository.findAll(pageable);
        List<DailyWord> dailyWords = page.getContent();
        List<DailyWordVo> vos = dailyWords.stream().map(this::getVo).collect(Collectors.toList());
        return pageUtil.listToPage(vos, page);
    }

    public DailyWordVo getVo(Integer id) {
        return getVo(find(id));
    }
    public DailyWord find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void add(DailyWordDto dto) throws Exception {
        DailyWord model = mapperFacade.map(dto, DailyWord.class);
        if (repository.findFirstByPublishedAtAfter(new Date()) != null) {
            throw new Exception("Existed");
        }
        String day = DateUtil.format(model.getPublishedAt(), DatePattern.NORM_DATE_PATTERN);
        if (null != repository.findFirstByDay(day)) {
            throw new Exception("Existed.");
        }
        model.setDay(day);
        repository.save(model);
    }

    public void del(Integer id) {
    }

    public void update(DailyWordDto dto) {
        DailyWord model = repository.findById(dto.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        mapperFacade.map(dto, model);
        model.setDay(DateUtil.format(model.getPublishedAt(), DatePattern.NORM_DATE_PATTERN));
        repository.save(model);
    }


}
