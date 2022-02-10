package com.example.cms.storage.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Accessors(chain = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer categoryId;
    private String title;
    private String images;
    private String link;
    private String content;
    private Boolean display;
    private Date publishedAt;
}
