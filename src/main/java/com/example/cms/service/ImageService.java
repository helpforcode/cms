package com.example.cms.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.example.cms.controller.criteria.ImageCriteria;
import com.example.cms.dto.ImageDto;
import com.example.cms.storage.entity.Image;
import com.example.cms.storage.entity.Tag;
import com.example.cms.storage.repository.ImageRepository;
import com.example.cms.vo.ImageVo;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
public class ImageService {

    @Value("${cms.image.root}")
    private String fileRoot;
    @Value("${cms.image.host}")
    private String imageHost;

    @Autowired
    private TagService tagService;
    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private ImageRepository repository;

    public Image saveFile(MultipartFile file) throws IOException {
        String fileName = generateFileName(file.getOriginalFilename());
        String relativePath = getRelativePath(fileName);
        Path path = Paths.get(fileRoot, relativePath);

        if (!Files.exists(path.getParent())) {
            // FileAttribute<Set<PosixFilePermission>> attribute = PosixFilePermissions
            //         .asFileAttribute(PosixFilePermissions.fromString("rwxrwxr--"));
            // Files.createDirectories(path.getParent(), attribute);
            Files.createDirectories(path.getParent());
        }
        file.transferTo(path);

        ImageDto dto = new ImageDto();
        dto.setName(fileName);
        dto.setPath(path.toString());
        dto.setUrl(getUrl(relativePath));
        dto.setSize(file.getSize());
        return add(dto);
    }

    private String getRelativePath(String fileName) {
        return String.format("/%s/%s", DateUtil.format(new Date(), DatePattern.SIMPLE_MONTH_PATTERN), fileName);
    }

    private static String generateFileName(String originName) {
        String ext = ".jpg";
        if (StringUtils.hasLength(originName)) {
            ext = originName.substring(originName.lastIndexOf("."));
        }
        return UUID.randomUUID() + ext;
    }

    private String getUrl(String relativePath) {
        return String.format("%s%s", imageHost, relativePath);
    }

    public Page<Image> list(ImageCriteria criteria, Pageable pageable) {
        // todo: use Predication
        Page<Image> images;
        if (CollectionUtil.isNotEmpty(criteria.getTagIds())) {
            images = repository.findAllByTagIds(criteria.getTagIds(), pageable);
        } else if (StringUtils.hasLength(criteria.getTagName())){
            images = repository.findAllByTagName(criteria.getTagName(), pageable);
        } else {
            images = repository.findAll(pageable);
        }
        return images;
    }

    private Image find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public ImageVo getImageVo(Integer id) {
        Image image = find(id);
        if (Objects.isNull(image)) {
            return null;
        }
        return mapperFacade.map(image, ImageVo.class);
    }

    public Image add(ImageDto dto) {
        Image model = mapperFacade.map(dto, Image.class);
        repository.save(model);
        return find(model.getId());
    }

    public void del(Integer id) {
        Image image = find(id);
        repository.deleteById(id);
        removeImage(image);
    }

    @Transactional
    public void del(Set<Integer> ids) {
        ids.forEach(this::del);
    }

    private void removeImage(Image image) {
        Path path = Paths.get(image.getPath());
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Transactional
    public void update(ImageDto dto) {
        Image model = repository.findById(dto.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }

        if (StringUtils.hasLength(dto.getName())) {
            model.setName(dto.getName());
        }

        if (!CollectionUtils.isEmpty(dto.getTagIds())) {
            List<Tag> tags = new ArrayList<>();
            for (Integer tagId : dto.getTagIds()) {
                Tag tag = tagService.find(tagId);
                tags.add(tag);
            }
            model.setTags(tags);
        }

        repository.save(model);
    }

}
