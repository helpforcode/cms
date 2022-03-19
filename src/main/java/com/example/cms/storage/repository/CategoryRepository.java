package com.example.cms.storage.repository;

import com.example.cms.storage.entity.Category;

import java.util.List;

public interface CategoryRepository extends BaseRepository<Category> {
    List<Category> findAllByDisplayEquals(boolean display);
}
