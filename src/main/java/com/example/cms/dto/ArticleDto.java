package com.example.cms.dto;

import lombok.Data;


@Data
public class ArticleDto {
    private Integer id;
    private Integer categoryId;
    private String title;
    private String images;
    private String link;
    private String content;
    private Boolean display;
    private String publishedAt;
}
