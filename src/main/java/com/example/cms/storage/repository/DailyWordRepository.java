package com.example.cms.storage.repository;

import com.example.cms.storage.entity.DailyWord;

import java.util.Date;

public interface DailyWordRepository extends BaseRepository<DailyWord> {
    DailyWord findFirstByStatusEqualsOrderByPublishedAtDesc(Integer status);
    DailyWord findFirstByDay(String day);
    DailyWord findFirstByPublishedAtAfter(Date date);
}
