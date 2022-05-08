package com.example.cms.controller.admin;

import com.example.cms.dto.InfoDto;
import com.example.cms.service.InfoService;
import com.example.cms.storage.entity.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/info")
@RestController("infoController")
public class InfoController {

    @Autowired
    private InfoService service;

    @PostMapping
    public void add(InfoDto info) {
        service.add(info);
    }

    @GetMapping
    public List<Info> infoList(Integer cateId) {
        return service.infoList(cateId);
    }

    @PutMapping("/{id}")
    public void update(InfoDto info, @PathVariable Integer id) {
        info.setId(id);
        service.update(info);
    }

    @GetMapping("/{id}")
    public Info detail(@PathVariable Integer id) {
        return service.detail(id);
    }
}
