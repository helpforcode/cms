package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.ArticleDto;
import com.example.cms.service.ArticleService;
import com.example.cms.storage.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService service;

    @AdminLogin
    @PostMapping
    public void add(@RequestBody ArticleDto dto) {
        service.add(dto);
    }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        service.del(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, ArticleDto dto) {
        dto.setId(id);
        service.update(dto);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public Article find(@PathVariable Integer id) {
        return service.find(id);
    }

    @GetMapping
    public Page<Article> list(Pageable pageable) {
        return service.list(pageable);
    }

}
