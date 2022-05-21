package com.example.cms.storage.repository;

import com.example.cms.storage.entity.WordProp;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface WordPropRepository extends BaseRepository<WordProp> {
    @Modifying
    @Query("delete from WordProp where propType = :propType")
    void deleteAllByPropType(Integer propType);
}
