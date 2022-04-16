package com.example.cms.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class HistoryReq {
    private String year;
}
