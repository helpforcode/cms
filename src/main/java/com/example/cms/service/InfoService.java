package com.example.cms.service;

import com.example.cms.dto.InfoCateDto;
import com.example.cms.dto.InfoDto;
import com.example.cms.dto.InfoReq;
import com.example.cms.storage.entity.Info;
import com.example.cms.storage.entity.InfoCate;
import com.example.cms.storage.repository.InfoCateRepository;
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
    @Autowired
    private InfoCateRepository cateRepository;

    public Info detail(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Info> infoSiblings(InfoReq req) {
        return repository.findAllByCateIdEqualsAndTitleEqualsOrderByCodeDesc(req.getCateId(), req.getTitle());
    }

    public List<Info> infoList(Integer cateId, boolean onlyVisible) {
        if (null != cateId) {
            if (onlyVisible) {
                return repository.findAllByVisibleEqualsAndCateIdEqualsOrderByCodeDesc(true, cateId);
            } else {
                return repository.findAllByCateIdEqualsOrderByCodeDesc(cateId);
            }
        }
        if (onlyVisible) {
            return repository.findAllByVisibleEqualsOrderByCodeDesc(true);
        } else {
            return repository.findAllByOrderByCodeDesc();
        }
    }

    public void add(InfoDto info) {
        repository.save(mapperFacade.map(info, Info.class));
    }

    public List<InfoCate> cateList() {
        return cateRepository.findAll();
    }

    public void addCate(InfoCateDto infoCateDto) {
        cateRepository.save(mapperFacade.map(infoCateDto, InfoCate.class));
    }

    public void updateCate(InfoCateDto dto) {
        InfoCate cate = cateRepository.findById(dto.getId()).orElse(null);
        if (null != cate) {
            mapperFacade.map(dto, cate);
            cateRepository.save(cate);
        }
    }

    public void update(InfoDto info) {
        Info entity = detail(info.getId());
        if (null != entity) {
            mapperFacade.map(info, entity);
            repository.save(entity);
        }
    }

    public InfoCate cateDetail(Integer id) {
        return cateRepository.findById(id).orElse(null);
    }
}
