package com.example.cms.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.cms.dto.PropDto;
import com.example.cms.dto.PropWordDto;
import com.example.cms.dto.PropWordMove;
import com.example.cms.storage.entity.Prop;
import com.example.cms.storage.entity.PropType;
import com.example.cms.storage.entity.WordProp;
import com.example.cms.storage.repository.PropRepository;
import com.example.cms.storage.repository.PropTypeRepository;
import com.example.cms.storage.repository.WordPropRepository;
import com.example.cms.vo.PropTypeDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PropService {
    @Autowired
    private PropRepository propRepo;
    @Autowired
    private PropTypeRepository typeRepo;
    @Autowired
    private WordPropRepository wordPropRepo;
    @Autowired
    private MapperFacade mapperFacade;

    @Transactional
    public void saveAll(PropTypeDto input) {
        if (input.getTypeId() == null) {
            // to add maybe
            return;
        }
        deleteLinkByTypeId(input.getTypeId());
        List<WordProp> links = new ArrayList<>();
        for (PropTypeDto.PropVo prop : input.getProps()) {
            for (Integer word : prop.getWords()) {
                WordProp link = new WordProp();
                link.setPropId(prop.getPropId());
                link.setPropType(input.getTypeId());
                link.setWordId(word);
                links.add(link);
            }
        }
        wordPropRepo.saveAll(links);
    }

    public List<PropTypeDto> all() {
        List<PropTypeDto> list = new ArrayList<>();

        List<PropType> types = listType();
        List<Prop> props = listProp();
        Map<Integer, List<Prop>> propsMapByType = props.stream().collect(Collectors.groupingBy(Prop::getTypeId));
        List<WordProp> wordProps = listLink();

        Map<Integer, List<WordProp>> typeMap = wordProps.stream()
                .collect(Collectors.groupingBy(WordProp::getPropType));

        for (PropType type : types) {
            PropTypeDto propTypeDto = new PropTypeDto()
                    .setTypeId(type.getId())
                    .setName(type.getName());

            // todo
            List<Prop> propsOfType = propsMapByType.getOrDefault(type.getId(), Collections.emptyList());
            if (CollectionUtil.isEmpty(propsOfType)) {
                // no props
                list.add(propTypeDto);
                continue;
            }

            List<PropTypeDto.PropVo> propsVoOfType = new ArrayList<>();
            for (Prop prop : propsOfType) {
                PropTypeDto.PropVo propVo = new PropTypeDto.PropVo()
                        .setPropId(prop.getId())
                        .setName(prop.getName())
                        .setCode(prop.getCode())
                        // words
                        ;

                List<WordProp> linkOfType = typeMap.getOrDefault(type.getId(), Collections.emptyList());
                if (CollectionUtil.isEmpty(linkOfType)) {
                    // no words
                    propsVoOfType.add(propVo);
                    continue;
                }
                // group by propId
                Map<Integer, List<WordProp>> propGroup = linkOfType.stream()
                        .collect(Collectors.groupingBy(WordProp::getPropId));

                if (propGroup.containsKey(prop.getId())) {
                    propVo.setWords(propGroup.get(prop.getId())
                            .stream().map(WordProp::getWordId).collect(Collectors.toList()));
                }
                propsVoOfType.add(propVo);
            }
            propTypeDto.setProps(propsVoOfType);

            list.add(propTypeDto);
        }
        return list;
    }

    public PropType findType(Integer id) {
        return typeRepo.findById(id).orElse(null);
    }
    public void addType(com.example.cms.dto.PropTypeDto dto) {
        PropType propType = mapperFacade.map(dto, PropType.class);
        typeRepo.save(propType);
    }
    public void updateType(com.example.cms.dto.PropTypeDto dto) {
        PropType model = typeRepo.findById(dto.getId()).orElse(null);
        if (model == null) return;
        mapperFacade.map(dto, model);
        typeRepo.save(model);
    }
    public List<PropType> listType() {
        return typeRepo.findAll();
    }
    public void deleteType(Integer id) {
        Optional<PropType> type = typeRepo.findById(id);
        type.ifPresent(propType -> typeRepo.delete(propType));
    }

    public Prop findProp(Integer id) {
        return propRepo.findById(id).orElse(null);
    }
    public void addProp(PropDto dto) {
        Prop prop = mapperFacade.map(dto, Prop.class);
        propRepo.save(prop);
    }
    public void updateProp(PropDto dto) {
        Prop model = propRepo.findById(dto.getId()).orElse(null);
        if (model == null) return;
        mapperFacade.map(dto, model);
        propRepo.save(model);
    }
    public List<Prop> listProp() {
        return propRepo.findAll();
    }
    public void deleteProp(Integer id) {
        Optional<Prop> model = propRepo.findById(id);
        model.ifPresent(m -> propRepo.delete(m));
    }

    public WordProp findLink(Integer id) {
        return wordPropRepo.findById(id).orElse(null);
    }
    public void addLink(PropWordDto dto) {
        WordProp model = mapperFacade.map(dto, WordProp.class);
        wordPropRepo.save(model);
    }
    public void updateLink(PropWordDto dto) {
        WordProp model = wordPropRepo.findById(dto.getId()).orElse(null);
        if (model == null) return;
        mapperFacade.map(dto, model);
        wordPropRepo.save(model);
    }

    public void move(PropWordMove dto) {
        WordProp link = findLink(dto.getWordPropId());
        if (null == link) return;
        PropWordDto propWordDto = new PropWordDto();
        propWordDto.setWordId(link.getWordId());
        propWordDto.setPropType(link.getPropType());
        propWordDto.setPropId(dto.getTargetProp());

        addLink(propWordDto);
        deleteLink(dto.getWordPropId());
    }

    public List<WordProp> listLink() {
        return wordPropRepo.findAll();
    }
    public void deleteLink(Integer id) {
        Optional<WordProp> model = wordPropRepo.findById(id);
        model.ifPresent(m -> wordPropRepo.delete(m));
    }
    private void deleteLinkByTypeId(Integer typeId) {
        wordPropRepo.deleteAllByPropType(typeId);
    }
}
