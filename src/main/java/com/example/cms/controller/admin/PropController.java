package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.*;
import com.example.cms.service.PropService;
import com.example.cms.storage.entity.Prop;
import com.example.cms.storage.entity.PropType;
import com.example.cms.storage.entity.WordProp;
import com.example.cms.util.PageUtil;
import com.example.cms.vo.PropTypeDto;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/prop")
public class PropController {
    @Autowired
    private PropService service;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private PageUtil pageUtil;

    @AdminLogin
    @GetMapping("/all")
    public List<PropTypeDto> all() {
        return service.all();
    }
    @AdminLogin
    @PostMapping("/all")
    public void saveAll(@RequestBody PropTypeDto input) {
        service.saveAll(input);
    }

    @AdminLogin
    @PostMapping
    public void add(@RequestBody PropDto dto) {
        service.addProp(dto);
    }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        service.deleteProp(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody PropDto dto) {
        dto.setId(id);
        service.updateProp(dto);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public Prop find(@PathVariable Integer id) {
        return service.findProp(id);
    }

    @GetMapping
    public List<Prop> list() {
        return service.listProp();
    }


    @AdminLogin
    @PostMapping("/type")
    public void addType(@RequestBody com.example.cms.dto.PropTypeDto dto) {
        service.addType(dto);
    }

    @AdminLogin
    @DeleteMapping("/type/{id}")
    public void delType(@PathVariable Integer id) {
        service.deleteType(id);
    }

    @AdminLogin
    @PutMapping("/type/{id}")
    public void updateType(@PathVariable Integer id, @RequestBody com.example.cms.dto.PropTypeDto dto) {
        dto.setId(id);
        service.updateType(dto);
    }

    @AdminLogin
    @GetMapping("/type/{id}")
    public PropType findType(@PathVariable Integer id) {
        return service.findType(id);
    }

    @GetMapping("/type")
    public List<PropType> listType() {
        return service.listType();
    }


    @AdminLogin
    @PostMapping("/link")
    public void addLink(@RequestBody PropWordDto dto) {
        service.addLink(dto);
    }

    @AdminLogin
    @DeleteMapping("/link/{id}")
    public void delLink(@PathVariable Integer id) {
        service.deleteLink(id);
    }

    @AdminLogin
    @PutMapping("/link/{id}")
    public void updateLink(@PathVariable Integer id, @RequestBody PropWordDto dto) {
        dto.setId(id);
        service.updateLink(dto);
    }

    @AdminLogin
    @PutMapping("/link/move")
    public void move(@RequestBody PropWordMove dto) {
        service.move(dto);
    }

    @AdminLogin
    @GetMapping("/link/{id}")
    public WordProp findLink(@PathVariable Integer id) {
        return service.findLink(id);
    }

    @GetMapping("/link")
    public List<WordProp> listLink() {
        return service.listLink();
    }
}
