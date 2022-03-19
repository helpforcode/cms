package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.ArticleDto;
import com.example.cms.service.ArticleService;
import com.example.cms.util.PageUtil;
import com.example.cms.vo.ArticleAdminVo;
import com.example.cms.vo.ArticleGroup;
import com.example.cms.vo.ArticleVo;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService service;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private PageUtil pageUtil;

    @AdminLogin
    @PostMapping
    public void add(@RequestBody ArticleDto dto) {
        service.add(dto);
    }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        service.del(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody ArticleDto dto) {
        dto.setId(id);
        service.update(dto);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public ArticleAdminVo find(@PathVariable Integer id) {
        return mapperFacade.map(service.find(id), ArticleAdminVo.class);
    }

    @GetMapping
    public Page<ArticleVo> list(Pageable pageable) {
        return pageUtil.toPage(service.list(pageable), ArticleVo.class);
    }

    @GetMapping("/group")
    public List<ArticleGroup> group() {
        return service.group();
    }
}
