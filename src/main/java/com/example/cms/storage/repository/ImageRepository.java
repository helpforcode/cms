package com.example.cms.storage.repository;

import com.example.cms.storage.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;


public interface ImageRepository extends BaseRepository<Image> {
    @Query("select i from Image i join i.tags t where t.name like %?1% ")
    Page<Image> findAllByTagName(String tagName, Pageable pageable);

    @Query("select i from Image i join i.tags t where t.id in ?1")
    Page<Image> findAllByTagIds(Collection<Integer> ids, Pageable pageable);
}
