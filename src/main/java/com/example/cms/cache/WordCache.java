package com.example.cms.cache;

import com.example.cms.storage.entity.Word;
import com.example.cms.storage.repository.WordRepository;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
// @CacheConfig(cacheNames = "word")
public class WordCache {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private WordRepository repository;

    // https://stackoverflow.com/questions/50755692/hibernate-query-cache-lazyinitializationexception
    // @Cacheable(key = "#id", unless = "#result == null")// https://stackoverflow.com/questions/32482129/cacheable-kills-hibernate-session
    // @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public Word getWord(Integer id) {
        String key = "word::" + id;
        Word wordCache = (Word) redisTemplate.opsForValue().get(key);
        if (null != wordCache) {
            return wordCache;
        }
        return repository.getById(id);
    }
}
