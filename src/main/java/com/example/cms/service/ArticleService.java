package com.example.cms.service;

import com.example.cms.dto.ArticleDto;
import com.example.cms.dto.CategoryDto;
import com.example.cms.storage.entity.Article;
import com.example.cms.storage.repository.ArticleRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ArticleService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private ArticleRepository repository;

    public Page<Article> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Article find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void add(ArticleDto dto) {
        Article model = mapperFacade.map(dto, Article.class);
        repository.save(model);
    }

    public void del(Integer id) {
        Article model = repository.findById(id).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        model.setDisplay(false);
        repository.save(model);
    }

    public void update(ArticleDto dto) {
        Article model = repository.findById(dto.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        mapperFacade.map(dto, model);
        repository.save(model);
    }


}
