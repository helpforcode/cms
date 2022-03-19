package com.example.cms.controller.front;


import com.example.cms.service.DailyWordService;
import com.example.cms.vo.DailyWordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("frontDailyWordController")
@RequestMapping("/daily-word")
public class DailyWordController {
    @Autowired
    private DailyWordService service;

    @GetMapping("/latest")
    public DailyWordVo latest() {
        return service.latest();
    }
}
