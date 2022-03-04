package com.example.cms.dto;

import com.example.cms.storage.entity.Word;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class DailyWordReq {
    private Integer id;

    private Word primaryWord;
    private List<Word> words;
    private String day;
    private Integer status;
}
