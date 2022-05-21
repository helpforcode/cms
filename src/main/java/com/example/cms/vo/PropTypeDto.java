package com.example.cms.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

@Data
@Accessors(chain = true)
public class PropTypeDto {
    private Integer typeId;
    private String name;
    private List<PropVo> props;

    @Accessors(chain = true)
    @Data
    public static class PropVo {
        private Integer propId;
        private String name;
        private String code;
        private Boolean selected = false;
        private List<Integer> words = Collections.emptyList();
    }
}
