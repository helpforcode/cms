package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.TagDto;
import com.example.cms.service.TagService;
import com.example.cms.storage.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/tag")
public class TagController {
    @Autowired
    private TagService service;

    @AdminLogin
    @PostMapping
    public void add(@RequestBody TagDto dto) {
        service.add(dto);
    }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        service.del(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody TagDto dto) {
        dto.setId(id);
        service.update(dto);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public Tag find(@PathVariable Integer id) {
        return service.find(id);
    }

    @GetMapping
    public Page<Tag> list(Pageable pageable) {
        return service.list(pageable);
    }

}
