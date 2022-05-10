package com.example.cms.controller.admin;

import com.example.cms.dto.InfoAll;
import com.example.cms.dto.InfoCateDto;
import com.example.cms.dto.InfoDto;
import com.example.cms.service.InfoService;
import com.example.cms.storage.entity.Info;
import com.example.cms.storage.entity.InfoCate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/admin/info")
@RestController("infoController")
public class InfoController {

    @Autowired
    private InfoService service;

    @GetMapping("/all")
    public List<InfoAll> all() {
        List<InfoCate> cates = service.cateList();
        List<Info> infos = service.infoList(null);
        Map<Integer, List<Info>> infoMap = infos.stream().collect(Collectors.groupingBy(Info::getCateId));

        List<InfoAll> infoAlls = new ArrayList<>();
        cates.forEach(cate -> {
            InfoAll infoAll = new InfoAll();
            infoAll.setCateId(cate.getId());
            infoAll.setCateName(cate.getName());
            infoAll.setInfos(infoMap.getOrDefault(cate.getId(), Collections.emptyList()));
            infoAlls.add(infoAll);
        });
        return infoAlls;
    }

    @PostMapping
    public void add(@RequestBody InfoDto info) {
        service.add(info);
    }

    @GetMapping
    public List<Info> infoList(Integer cateId) {
        return service.infoList(cateId);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody InfoDto info, @PathVariable Integer id) {
        info.setId(id);
        service.update(info);
    }

    @GetMapping("/{id}")
    public Info detail(@PathVariable Integer id) {
        return service.detail(id);
    }


    @PostMapping("/cate")
    public void addCate(@RequestBody InfoCateDto infoCate) {
        service.addCate(infoCate);
    }

    @PutMapping("/cate/{id}")
    public void updateCate(@RequestBody InfoCateDto dto, @PathVariable Integer id) {
        dto.setId(id);
        service.updateCate(dto);
    }

    @GetMapping("/cate")
    public List<InfoCate> cateList() {
        return service.cateList();
    }

    @GetMapping("/cate/{id}")
    public InfoCate cateDetail(@PathVariable Integer id) {
        return service.cateDetail(id);
    }
}
