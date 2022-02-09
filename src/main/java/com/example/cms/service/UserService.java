package com.example.cms.service;

import com.example.cms.dto.UserDto;
import com.example.cms.storage.dao.UserDao;
import com.example.cms.storage.entity.User;
import com.example.cms.storage.repository.UserRepository;
import com.example.cms.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;
    @Autowired
    private UserDao dao;

    public void delById(Integer id) {
        repository.deleteById(id);
    }

    public User findOne() {
        return repository.getById(1);
    }

    public List<User> list() {
        return repository.findAll();
    }

    public void add(User user) {
        user.setPassword(TokenUtil.pwdEncrypt(user.getUsername(), user.getPassword()));
        dao.add(user);
    }

    public String login(UserDto dto) throws Exception {
        User user = repository.findByUsername(dto.getUsername());
        if (Objects.isNull(user)) {
            throw new Exception("Username or Password is incorrect");
        }
        if (!user.getPassword().equals(TokenUtil.pwdEncrypt(dto.getUsername(), dto.getPassword()))) {
            throw new Exception("Username or Password is incorrect");
        }
        return TokenUtil.getToken(user);
    }
}
