package com.example.cms.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.cms.dto.ImgTagDto;
import com.example.cms.dto.TagDto;
import com.example.cms.storage.entity.ImgTag;
import com.example.cms.storage.entity.Tag;
import com.example.cms.storage.repository.ImgTagRepository;
import com.example.cms.storage.repository.TagRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImgTagService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private ImgTagRepository repository;

    public Page<ImgTag> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public ImgTag find(Integer id) {
        return repository.findById(id).orElse(null);
    }
    public List<ImgTag> findByImg(Integer imgId) {
        return repository.findAllByImgId(imgId);
    }

    public void add(ImgTagDto dto) {
        if (CollectionUtil.isNotEmpty(dto.getTagIds())) {
            List<ImgTag> models = dto.getTagIds().stream()
                    .map(tagId -> new ImgTag().setTagId(tagId).setImgId(dto.getImgId()))
                    .collect(Collectors.toList());
            repository.saveAll(models);
        }
    }

    public void delByImgId(Integer imgId) {
        repository.deleteAllByImgId(imgId);
    }

    public void delByTagId(Integer tagId) {
        repository.deleteAllByTagId(tagId);
    }

    public void del(Integer id) {
        repository.deleteById(id);
    }

    public void update(ImgTagDto dto) {
        ImgTag model = repository.findById(dto.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        mapperFacade.map(dto, model);
        repository.save(model);
    }


}
