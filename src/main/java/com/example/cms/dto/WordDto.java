package com.example.cms.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class WordDto {
    private Integer id;

    private String word;
}
