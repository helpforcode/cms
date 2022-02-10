package com.example.cms.service;

import com.example.cms.dto.DailyWordDto;
import com.example.cms.dto.WordDto;
import com.example.cms.storage.entity.DailyWord;
import com.example.cms.storage.repository.DailyWordRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DailyWordService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private DailyWordRepository repository;

    public Page<DailyWord> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public DailyWord find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void add(DailyWordDto dto) {
        DailyWord model = mapperFacade.map(dto, DailyWord.class);
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
        repository.save(model);
    }


}
