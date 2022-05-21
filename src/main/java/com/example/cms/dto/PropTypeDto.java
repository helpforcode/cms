package com.example.cms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PropTypeDto implements Serializable {
    private Integer id;
    private String name;
    private String code;
}
