package com.example.cms.controller;

import com.example.cms.dto.UserDto;
import com.example.cms.service.UserService;
import com.example.cms.storage.dao.UserDao;
import com.example.cms.storage.entity.User;
import com.sun.javaws.progress.PreloaderPostEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @DeleteMapping
    public void del(Integer id) {
        service.delById(id);
    }

    @GetMapping("/list")
    public List<User> list() {
        return service.list();
    }

    @PostMapping("/add")
    public void add(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        service.add(user);
    }

    @PostMapping("/login")
    public String login(UserDto dto) throws Exception {
        return service.login(dto);
    }
}
