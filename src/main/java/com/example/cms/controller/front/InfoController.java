package com.example.cms.controller.front;

import com.example.cms.dto.InfoAll;
import com.example.cms.dto.InfoReq;
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

@RequestMapping("/info")
@RestController("frontInfoController")
public class InfoController {
    @Autowired
    private InfoService service;

    @GetMapping
    public List<Info> infoList() {
        return service.infoList(null, true);
    }

    @GetMapping("/siblings")
    public List<Info> infoSiblings(InfoReq req) {
        return service.infoSiblings(req);
    }

    // todo: cache
    @GetMapping("/all")
    public List<InfoAll> all() {
        List<InfoCate> cates = service.cateList();
        List<Info> infos = service.infoList(null, false);
        Map<Integer, List<Info>> infoMap = infos.stream().collect(Collectors.groupingBy(Info::getCateId));

        List<InfoAll> infoAlls = new ArrayList<>();
        cates.forEach(cate -> {
            InfoAll infoAll = new InfoAll();
            infoAll.setCateId(cate.getId());
            infoAll.setCateName(cate.getName());
            infoAll.setClickable(cate.getClickable());
            infoAll.setInfos(infoMap.getOrDefault(cate.getId(), Collections.emptyList()));
            infoAlls.add(infoAll);
        });
        return infoAlls;
    }
}
