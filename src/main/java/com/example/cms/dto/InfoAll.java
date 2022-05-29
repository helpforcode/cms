package com.example.cms.dto;

import com.example.cms.storage.entity.Info;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class InfoAll {
    private Integer cateId;
    private String cateName;
    private Boolean clickable;
    private Boolean operating = false;
    private List<Info> infos;
}
