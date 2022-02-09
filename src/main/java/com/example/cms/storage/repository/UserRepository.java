package com.example.cms.storage.repository;

import com.example.cms.storage.entity.User;

public interface UserRepository extends BaseRepository<User> {
    User findByUsername(String username);
}
