package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.DailyWordDto;
import com.example.cms.dto.DailyWordReq;
import com.example.cms.dto.HistoryReq;
import com.example.cms.service.DailyWordService;
import com.example.cms.storage.entity.DailyWord;
import com.example.cms.vo.DailyWordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/daily-word")
public class DailyWordController {
    @Autowired
    private DailyWordService service;

    @AdminLogin
    @PostMapping
    public void add(@RequestBody DailyWordReq req) throws Exception {
        service.add(req);
    }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        service.del(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody DailyWordReq req) {
        req.setId(id);
        service.update(req);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public DailyWordVo find(@PathVariable Integer id) {
        return service.getVo(id);
    }

    @AdminLogin
    @GetMapping("/latest")
    public DailyWordVo latest() {
        return service.latest();
    }

    @AdminLogin
    @GetMapping("/next")
    public DailyWordVo next() {
        return service.next();
    }

    @GetMapping
    public Page<DailyWordVo> list(Pageable pageable) {
        return service.list(pageable);
    }

    @GetMapping("/all")
    public List<DailyWordVo> all(HistoryReq req) {
        return service.all(req);
    }


}
