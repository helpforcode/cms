package com.example.cms.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PropWordDto implements Serializable {
    private Integer id;
    private Integer wordId;
    private Integer propType;
    private Integer propId;
}
