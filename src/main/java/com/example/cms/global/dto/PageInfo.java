package com.example.cms.global.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo<T> {
    private List<T> content;
    private Info pageInfo;

    @Data
    public static class Info {
        // page number
        private int page;
        // page size
        private int pageSize;
        private int totalPages;
        private long totalElements;
        private int numberOfElements;
    }
}
