package com.example.cms.cache;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
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
        String key = getKey(year);

        String jsonList = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.hasLength(jsonList)) {
            return JSON.parseArray(jsonList, DailyWordVo.class);
        }

        List<DailyWord> dailyWords = repository.findAllByDayStartsWithOrderByIdDesc(year);

        // List<Object> dailyWordsCache = redisTemplate.opsForList().range(key, 0, -1);
        // if (CollectionUtil.isEmpty(dailyWordsCache)) {
        //     // fetch from db
        //     // cache
        //     redisTemplate.opsForList().leftPushAll(key, dailyWords);
        // } else {
        //     dailyWords = mapperFacade.mapAsList(dailyWordsCache, DailyWord.class);
        // }

        List<DailyWordVo> dailyWordVos = dailyWords.stream().map(dailyWordService::getVo).collect(Collectors.toList());

        redisTemplate.opsForValue().set(key, JSON.toJSONString(dailyWordVos));
        return dailyWordVos;
    }
    private String getKey(String year) {
        return "dailyWordVo::" + year;
    }

    // @CacheEvict(key = "#year")
    public void flushAll(String year) {
        redisTemplate.delete(getKey(year));
    }

    public String latestCode() {
        String key = "dailyWord::latestCode";
        Object value = redisTemplate.opsForValue().get(key);
        String code;
        if (null == value) {
            code = dailyWordService.latest().getCode();
            redisTemplate.opsForValue().set(key, code);
        } else {
            code = (String) value;
        }
        return code;
    }
}
