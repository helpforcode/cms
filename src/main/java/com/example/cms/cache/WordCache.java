package com.example.cms.cache;

import com.example.cms.storage.entity.Word;
import com.example.cms.storage.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "word")
public class WordCache {

    @Autowired
    private WordRepository repository;

    @Cacheable(key = "#id")
    public Word getWord(Integer id) {
        return repository.getById(id);
    }
}
