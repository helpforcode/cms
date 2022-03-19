package com.example.cms.storage.repository;

import com.example.cms.storage.entity.Article;

import java.util.List;

public interface ArticleRepository extends BaseRepository<Article> {
    List<Article> findAllByDisplayEquals(boolean display);
}
