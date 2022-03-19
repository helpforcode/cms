package com.example.cms.service;

import com.example.cms.dto.ArticleDto;
import com.example.cms.storage.entity.Article;
import com.example.cms.storage.entity.Category;
import com.example.cms.storage.repository.ArticleRepository;
import com.example.cms.vo.ArticleGroup;
import com.example.cms.vo.ArticleRow;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private ArticleRepository repository;
    @Autowired
    private CategoryService categoryService;

    public List<ArticleGroup> group() {
        List<Category> categories = categoryService.availableCate();
        // manual control, instead of automatically off by time.
        List<Article> articles = availableArticles();

        Map<Integer, List<Article>> articleMap = articles.stream().collect(Collectors.groupingBy(Article::getCategoryId));

        return categories.stream().map(category -> {
            ArticleGroup group = new ArticleGroup();
            group.setCategoryId(category.getId());
            group.setCategoryCode(category.getCode());
            group.setCategoryName(category.getName());
            group.setArticles(mapperFacade.mapAsList(articleMap.getOrDefault(category.getId(), Collections.emptyList()), ArticleRow.class));
            return group;
        }).collect(Collectors.toList());
    }

    private List<Article> availableArticles() {
        return repository.findAllByDisplayEquals(true);
    }

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
