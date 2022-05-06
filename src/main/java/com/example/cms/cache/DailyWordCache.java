package com.example.cms.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.example.cms.service.DailyWordService;
import com.example.cms.storage.entity.DailyWord;
import com.example.cms.storage.repository.DailyWordRepository;
import com.example.cms.vo.DailyWordVo;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
// @CacheConfig(cacheNames = "dailyWord")
public class DailyWordCache {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DailyWordRepository repository;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private DailyWordService dailyWordService;

    // @Cacheable(key = "#year")
    public List<DailyWordVo> all(String year) {
        if (!StringUtils.hasLength(year)) {
            year = String.valueOf(DateUtil.year(new Date()));
        }
        List<DailyWord> dailyWords = new ArrayList<>();

        String key = getKey(year);
        List<Object> dailyWordsCache = redisTemplate.opsForList().range(key, 0, -1);
        if (CollectionUtil.isEmpty(dailyWordsCache)) {
            // fetch from db
            dailyWords = repository.findAllByDayStartsWithOrderByIdDesc(year);
            // cache
            redisTemplate.opsForList().leftPushAll(key, dailyWords);
        } else {
            dailyWords = mapperFacade.mapAsList(dailyWordsCache, DailyWord.class);
        }
        return dailyWords.stream().map(dailyWordService::getVo).collect(Collectors.toList());
    }
    private String getKey(String year) {
        return "dailyWord::" + year;
    }

    // @CacheEvict(key = "#year")
    public void flushAll(String year) {
        redisTemplate.delete(getKey(year));
    }
}
