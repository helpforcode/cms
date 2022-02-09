package com.example.cms.service;

import com.example.cms.dto.CategoryDto;
import com.example.cms.storage.entity.Category;
import com.example.cms.storage.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    // todo page, sort
    public Page<Category> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Category find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void add(CategoryDto dto) {
        Category model = new Category()
                .setName(dto.getName())
                .setCode(dto.getCode());

        repository.save(model);
    }

    public void del(Integer id) {
        Category model = repository.findById(id).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        model.setDisplay(false);
        repository.save(model);
    }

    public void update(CategoryDto dto) {
        Category model = repository.findById(dto.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        model.setName(dto.getName());
        model.setCode(dto.getCode());
        repository.save(model);
    }


}
