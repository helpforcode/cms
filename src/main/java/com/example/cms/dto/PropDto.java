package com.example.cms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PropDto implements Serializable {
    private Integer id;
    private Integer typeId;
    private String name;
    private String code;
}
