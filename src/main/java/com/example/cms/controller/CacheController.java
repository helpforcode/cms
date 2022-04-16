package com.example.cms.controller;

import com.example.cms.cache.WordCache;
import com.example.cms.service.WordService;
import com.example.cms.storage.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController {
    @Autowired
    private WordService wordService;
    @Autowired
    private WordCache cache;

    @RequestMapping("/cache-words")
    public void cacheWords() {
        List<Word> words = wordService.list();
        words.forEach(word -> {
            cache.getWord(word.getId());
        });
    }

    @GetMapping("/get-word")
    public Word getWord(Integer id) {
        return cache.getWord(id);
    }
}
