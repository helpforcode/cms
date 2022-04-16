package com.example.cms.service;

import com.example.cms.cache.WordCache;
import com.example.cms.dto.WordDto;
import com.example.cms.storage.entity.Word;
import com.example.cms.storage.repository.WordRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class WordService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private WordRepository repository;

    public List<Word> list() {
        return repository.findAll();
    }

    public Word find(Integer id) {
        return cache.getWord(id);
    }

    public void add(WordDto dto) {
        Word model = mapperFacade.map(dto, Word.class);
        repository.save(model);
    }

    public void del(Integer id) {
    }

    public void update(WordDto dto) {
        Word model = repository.findById(dto.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        mapperFacade.map(dto, model);
        repository.save(model);
    }

    @Autowired
    private WordCache cache;

    public List<Word> words(Set<Integer> ids) {
        List<Word> words = new ArrayList<>();
        for (Integer id : ids) {
            words.add(cache.getWord(id));
        }
        return words;
    }

}
