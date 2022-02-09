package com.example.cms.storage.dao;

import com.example.cms.storage.entity.User;
import com.example.cms.storage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private UserRepository repository;

    public User add(User user) {
        return repository.save(user);
    }
}
