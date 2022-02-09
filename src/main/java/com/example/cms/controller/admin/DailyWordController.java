package com.example.cms.controller.admin;

import com.example.cms.dto.CategoryDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-word")
public class DailyWordController {

    @PostMapping
    public void add(CategoryDto dto) {

    }

    @DeleteMapping
    public void del() {

    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id) {

    }

    @GetMapping("/{id}")
    public void find(@PathVariable Integer id) {

    }
}
