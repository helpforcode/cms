package com.example.cms.dto;

import com.example.cms.storage.entity.Info;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InfoDto {

    private Integer id;
    private Integer code;

    private String title;
    private Integer cateId;
    private String remark;
    private Boolean visible;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}
