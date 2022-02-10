package com.example.cms.service;

import com.example.cms.dto.MenuDto;
import com.example.cms.storage.entity.Menu;
import com.example.cms.storage.repository.MenuRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MenuService {

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private MenuRepository repository;

    public Page<Menu> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Menu find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void add(MenuDto dto) {
        Menu model = mapperFacade.map(dto, Menu.class);
        repository.save(model);
    }

    public void del(Integer id) {
    }

    public void update(MenuDto dto) {
        Menu model = repository.findById(dto.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        mapperFacade.map(dto, model);
        repository.save(model);
    }


}
