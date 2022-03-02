package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.CategoryDto;
import com.example.cms.service.CategoryService;
import com.example.cms.storage.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @AdminLogin
    @PostMapping
    public void add(CategoryDto dto) {
        service.add(dto);
    }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        service.del(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, CategoryDto dto) {
        dto.setId(id);
        service.update(dto);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public Category find(@PathVariable Integer id) {
        return service.find(id);
    }

    @GetMapping
    public Page<Category> list(Pageable pageable) {
        return service.list(pageable);
    }
}
