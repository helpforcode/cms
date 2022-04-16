package com.example.cms.cache;

import cn.hutool.core.date.DateUtil;
import com.example.cms.service.DailyWordService;
import com.example.cms.storage.repository.DailyWordRepository;
import com.example.cms.vo.DailyWordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "dailyWord")
public class DailyWordCache {

    @Autowired
    private DailyWordRepository repository;
    @Autowired
    private DailyWordService dailyWordService;

    @Cacheable(key = "#year")
    public List<DailyWordVo> all(String year) {
        if (!StringUtils.hasLength(year)) {
            year = String.valueOf(DateUtil.year(new Date()));
        }
        return repository.findAllByDayStartsWithOrderByIdDesc(year).stream().map(dailyWordService::getVo).collect(Collectors.toList());
    }

    @CacheEvict(key = "#year")
    public void flushAll(String year) {

    }
}
