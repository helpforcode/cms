package com.example.cms.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class DailyWordDto {
    private Integer id;

    private String words;
    private String wordPrimary;
    private String publishedAt;
    private Boolean status;
}
