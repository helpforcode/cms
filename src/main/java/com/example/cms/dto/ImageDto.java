package com.example.cms.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Accessors(chain = true)
@Data
public class ImageDto {
    private Integer id;
    private String name;
    private String path;
    private String url;
    private Long size;
    private List<Integer> tagIds;
}
