package com.example.cms.service;

import com.example.cms.dto.TagDto;
import com.example.cms.storage.entity.Tag;
import com.example.cms.storage.repository.TagRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TagService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private TagRepository repository;

    public Page<Tag> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Tag find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void add(TagDto dto) {
        Tag model = mapperFacade.map(dto, Tag.class);
        repository.save(model);
    }

    public void del(Integer id) {
        repository.deleteById(id);
    }

    public void update(TagDto dto) {
        Tag model = repository.findById(dto.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        mapperFacade.map(dto, model);
        repository.save(model);
    }


}
