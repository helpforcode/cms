package com.example.cms.vo;

import com.example.cms.storage.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ImageVo {
    private Integer id;
    private String name;
    private String url;
    private String relativeUrl;
    private List<Tag> tags;
}

