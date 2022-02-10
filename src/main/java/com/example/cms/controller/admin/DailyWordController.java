package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.DailyWordDto;
import com.example.cms.service.DailyWordService;
import com.example.cms.storage.entity.DailyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-word")
public class DailyWordController {
    @Autowired
    private DailyWordService service;

    @AdminLogin
    @PostMapping
    public void add(DailyWordDto dto) {
        service.add(dto);
    }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        service.del(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, DailyWordDto dto) {
        dto.setId(id);
        service.update(dto);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public DailyWord find(@PathVariable Integer id) {
        return service.find(id);
    }

    @GetMapping
    public Page<DailyWord> list(Pageable pageable) {
        return service.list(pageable);
    }

}
