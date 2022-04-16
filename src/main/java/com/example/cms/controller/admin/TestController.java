package com.example.cms.controller.admin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.example.cms.cache.DailyWordCache;
import com.example.cms.dto.DailyWordReq;
import com.example.cms.service.DailyWordService;
import com.example.cms.service.WordService;
import com.example.cms.storage.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/test")
public class TestController {

    @Autowired
    private DailyWordService service;
    @Autowired
    private WordService wordService;

    @Autowired
    private DailyWordCache cache;

    @GetMapping
    public void test(int year) throws Exception {
        int nowY = DateUtil.year(new Date());

        cache.flushAll(String.valueOf(year));

        List<Word> words = wordService.list();

        boolean exit = false;
        for (int m=1; m<=12; m++) {
            int dLen = DateUtil.lengthOfMonth(m, DateUtil.isLeapYear(year));

            for (int d=1; d<=dLen; d++) {
                String month = m < 10 ? "0"+m : String.valueOf(m);
                String day = d < 10 ? "0"+d : String.valueOf(d);
                String dayStr = String.format("%s-%s-%s", year, month, day);
                if (nowY == year) {
                    String today = DateUtil.format(new Date(), "YYYY-mm-dd");
                    if (dayStr.equals(today)) {
                        exit = true;
                        break;
                    }
                }
                Set<Word> ws = RandomUtil.randomEleSet(words, 6);
                Word pw = RandomUtil.randomEle(words);

                DailyWordReq req = new DailyWordReq();
                req.setDay(dayStr);
                req.setPrimaryWord(pw);
                req.setWords(ws);
                req.setStatus(true);

                service.add(req);
            }
            if (exit) {
                break;
            }
        }
    }

}
