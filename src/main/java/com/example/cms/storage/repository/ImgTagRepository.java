package com.example.cms.storage.repository;

import com.example.cms.storage.entity.ImgTag;

import java.util.List;

public interface ImgTagRepository extends BaseRepository<ImgTag> {
    void deleteAllByImgId(Integer imgId);
    void deleteAllByTagId(Integer tagId);
    List<ImgTag> findAllByImgId(Integer imgId);
}
