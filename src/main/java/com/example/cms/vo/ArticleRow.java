package com.example.cms.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleRow {
    private Integer id;
    private Integer categoryId;
    private String title;
    private String link;
    private Date publishedAt;
}
