package com.example.cms.controller.front;

import com.example.cms.service.InfoService;
import com.example.cms.storage.entity.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/info")
@RestController("frontInfoController")
public class InfoController {
    @Autowired
    private InfoService service;

    @GetMapping
    public List<Info> infoList() {
        return service.infoList(null);
    }
}
