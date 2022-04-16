package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.WordDto;
import com.example.cms.service.WordService;
import com.example.cms.storage.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/word")
public class WordController {
    @Autowired
    private WordService service;

    @AdminLogin
    @PostMapping
    public void add(WordDto dto) {
        service.add(dto);
    }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) {
        service.del(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, WordDto dto) {
        dto.setId(id);
        service.update(dto);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public Word find(@PathVariable Integer id) {
        return service.find(id);
    }

    @GetMapping
    public List<Word> list() {
        return service.list();
    }


    @GetMapping("/distinct")
    public List<String> distinct() {
        List<Word> words = service.list();
        return words.stream().map(Word::getWord).distinct().collect(Collectors.toList());
    }

}
