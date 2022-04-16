package com.example.cms.dto;

import com.example.cms.storage.entity.Word;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Accessors(chain = true)
@Data
public class DailyWordReq {
    private Integer id;

    private Word primaryWord;
    private Set<Word> words;
    private String day;
    private Boolean status;
}
