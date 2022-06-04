package com.example.cms.controller.front;

import com.example.cms.service.ArticleService;
import com.example.cms.vo.ArticleVo;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("FrontArticle")
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService service;
    @Autowired
    private MapperFacade mapperFacade;

    @GetMapping("/{id}")
    public ArticleVo find(@PathVariable Integer id) {
        return mapperFacade.map(service.find(id), ArticleVo.class);
    }
}
