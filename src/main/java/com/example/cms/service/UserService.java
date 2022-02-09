package com.example.cms.service;

import cn.hutool.crypto.SecureUtil;
import com.example.cms.dto.UserDto;
import com.example.cms.storage.dao.UserDao;
import com.example.cms.storage.entity.User;
import com.example.cms.storage.repository.UserRepository;
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
        user.setPassword(pwdEncrypt(user.getUsername(), user.getPassword()));
        dao.add(user);
    }

    public String login(UserDto dto) throws Exception {
        User user = repository.findByUsername(dto.getUsername());
        if (Objects.isNull(user)) {
            throw new Exception("Username or Password is incorrect");
        }
        if (!user.getPassword().equals(pwdEncrypt(dto.getUsername(), dto.getPassword()))) {
            throw new Exception("Username or Password is incorrect");
        }
        return "token for you";
    }

    private String pwdEncrypt(String username, String password) {
        return SecureUtil.sha1(String.format("%s%s%s", username, salt, password));
    }

    private final String salt = "encryptor";
}
