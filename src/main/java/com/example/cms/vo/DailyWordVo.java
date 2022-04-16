package com.example.cms.vo;

import com.example.cms.storage.entity.Word;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DailyWordVo implements Serializable {
    private Integer id;
    private Word primaryWord;
    private List<Word> words;
    private Date publishedAt;
    private String day;
    private Boolean status;
    private String code;
}
