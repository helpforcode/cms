package com.example.cms.controller.front;

import com.example.cms.service.PropService;
import com.example.cms.vo.PropTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("FrontPropController")
@RequestMapping("/prop")
public class PropController {

    @Autowired
    private PropService service;

    @GetMapping("/all")
    public List<PropTypeDto> all() {
        // todo: cache
        return service.all();
    }
}
