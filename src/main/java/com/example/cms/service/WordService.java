package com.example.cms.service;

import com.example.cms.dto.WordDto;
import com.example.cms.storage.entity.Word;
import com.example.cms.storage.repository.WordRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class WordService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private WordRepository repository;

    public Page<Word> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Word find(Integer id) {
        return repository.findById(id).orElse(null);
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


}
