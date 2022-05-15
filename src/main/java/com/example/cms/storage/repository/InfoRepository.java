package com.example.cms.storage.repository;

import com.example.cms.storage.entity.Info;

import java.util.List;

public interface InfoRepository extends BaseRepository<Info> {

    List<Info> findAllByVisibleEqualsOrderByCodeDesc(Boolean visible);
    List<Info> findAllByVisibleEqualsAndCateIdEqualsOrderByCodeDesc(Boolean visible, Integer cateId);

    List<Info> findAllByOrderByCodeDesc();
    List<Info> findAllByCateIdEqualsOrderByCodeDesc(Integer cateId);
}
