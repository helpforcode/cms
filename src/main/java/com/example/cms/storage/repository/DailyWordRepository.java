package com.example.cms.storage.repository;

import com.example.cms.storage.entity.DailyWord;

import java.util.Date;
import java.util.List;

public interface DailyWordRepository extends BaseRepository<DailyWord> {
    DailyWord findFirstByStatusEqualsOrderByPublishedAtDesc(Boolean status);
    DailyWord findFirstByStatusEqualsAndPublishedAtGreaterThanOrderByPublishedAtDesc(Boolean status, Date now);
    DailyWord findFirstByDay(String day);
    DailyWord findFirstByPublishedAtAfter(Date date);
    DailyWord findFirstByDayStartsWithOrderByCodeDesc(String year);
    List<DailyWord> findAllByDayStartsWithOrderByIdDesc(String year);
}
