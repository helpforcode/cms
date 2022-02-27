package com.example.cms.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ImageVo {
    private Integer id;
    private String name;
    private String url;
    private List<Tag> tags;

    @Setter
    @Getter
    public static class Tag {
        public Tag(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
        private Integer id;
        private String name;
    }
}

