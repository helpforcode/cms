package com.example.cms.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleGroup {

    private Integer categoryId;
    private String  categoryName;
    private String  categoryCode;

    List<ArticleRow> articles;
}
