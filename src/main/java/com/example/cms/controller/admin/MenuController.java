package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.MenuDto;
import com.example.cms.service.MenuService;
import com.example.cms.storage.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/menu")
public class MenuController {
    @Autowired
    private MenuService service;

    @AdminLogin
    @PostMapping
    public void add(MenuDto dto) {
        service.add(dto);
    }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        service.del(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, MenuDto dto) {
        dto.setId(id);
        service.update(dto);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public Menu find(@PathVariable Integer id) {
        return service.find(id);
    }

    @GetMapping
    public Page<Menu> list(Pageable pageable) {
        return service.list(pageable);
    }

}
