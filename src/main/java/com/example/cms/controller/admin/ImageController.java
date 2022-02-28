package com.example.cms.controller.admin;

import com.example.cms.annotation.AdminLogin;
import com.example.cms.dto.ImageDto;
import com.example.cms.service.ImageService;
import com.example.cms.storage.entity.Image;
import com.example.cms.vo.ImageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/admin/image")
public class ImageController {

    @Autowired
    private ImageService service;


    @PostMapping("/upload")
    public Image upload(MultipartFile file) throws IOException {
        log.info("File:{}", file);
        return service.saveFile(file);
    }

    // @AdminLogin
    // @PostMapping
    // public void add(@RequestBody ImageDto dto) {
    //     service.add(dto);
    // }

    @AdminLogin
    @DeleteMapping("/{id}")
    public void del(@PathVariable Integer id) throws IOException {
        service.del(id);
    }

    @AdminLogin
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody ImageDto dto) {
        dto.setId(id);
        service.update(dto);
    }

    @AdminLogin
    @GetMapping("/{id}")
    public ImageVo find(@PathVariable Integer id) {
        return service.getImageVo(id);
    }

    /**
     * todo: filter by tagId (join tables)
     * @param pageable
     * @return
     */
    @GetMapping
    public Page<Image> list(Pageable pageable) {
        return service.list(pageable);
    }

}
