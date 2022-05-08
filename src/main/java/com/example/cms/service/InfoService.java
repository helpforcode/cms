package com.example.cms.service;

import com.example.cms.dto.InfoDto;
import com.example.cms.storage.entity.Info;
import com.example.cms.storage.repository.InfoRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoService {

    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private InfoRepository repository;

    public Info detail(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Info> infoList(Integer cateId) {
        if (null != cateId) {
            return repository.findAllByVisibleEqualsAndCateIdEqualsOrderByCodeDesc(true, cateId);
        }
        return repository.findAllByVisibleEqualsOrderByCodeDesc(true);
    }

    public void add(InfoDto info) {
        repository.save(mapperFacade.map(info, Info.class));
    }

    public void update(InfoDto info) {
        Info entity = detail(info.getId());
        if (null != entity) {
            mapperFacade.map(info, entity);
            repository.save(entity);
        }
    }
}
