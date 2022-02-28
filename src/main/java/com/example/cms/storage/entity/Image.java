package com.example.cms.storage.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Accessors(chain = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String path;
    private String url;
    private Long size;
    private Date created;

    @ManyToMany
    @JoinTable(
            name = "img_tag",
            joinColumns = @JoinColumn(name="img_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")

    )
    private List<Tag> tags;
}
