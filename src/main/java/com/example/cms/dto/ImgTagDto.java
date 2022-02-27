package com.example.cms.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class ImgTagDto {
    private Integer id;

    private List<Integer> tagIds;
    private Integer imgId;
}
