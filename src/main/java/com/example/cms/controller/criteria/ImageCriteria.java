package com.example.cms.controller.criteria;

import lombok.Data;

import java.util.Set;

@Data
public class ImageCriteria {
    private String tagName;
    private Set<Integer> tagIds;
}
