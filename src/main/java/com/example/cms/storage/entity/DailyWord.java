package com.example.cms.storage.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class DailyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String words;
    private Integer wordPrimary;
    private Date publishedAt;
    private String day;
    private Integer status;
}
