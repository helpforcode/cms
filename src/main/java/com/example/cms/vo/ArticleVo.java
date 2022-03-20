package com.example.cms.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleVo {
    private Integer id;
    private Integer categoryId;
    private String title;
    private List<String> images;
    private String link;
    private String content;
    private Boolean display;
    private String publishedAt;
}
