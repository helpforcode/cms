package com.example.cms.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TagDto {
    private Integer id;

    private String name;
}
