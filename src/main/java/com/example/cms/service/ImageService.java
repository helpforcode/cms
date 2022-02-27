package com.example.cms.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.example.cms.dto.ImageDto;
import com.example.cms.dto.ImgTagDto;
import com.example.cms.storage.entity.Image;
import com.example.cms.storage.entity.ImgTag;
import com.example.cms.storage.entity.Tag;
import com.example.cms.storage.repository.ImageRepository;
import com.example.cms.vo.ImageVo;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Value("${cms.image.root}")
    private String fileRoot;
    @Value("${cms.image.host}")
    private String imageHost;

    @Autowired
    private ImgTagService imgTagService;
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

    public Page<Image> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Image find(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public ImageVo getImageVo(Integer id) {
        Image image = find(id);
        if (Objects.isNull(image)) {
            return null;
        }
        ImageVo imageVo = mapperFacade.map(image, ImageVo.class);
        List<ImgTag> imgTags = imgTagService.findByImg(id);
        if (CollectionUtil.isNotEmpty(imgTags)) {
            List<Tag> tags = tagService.findByIds(imgTags.stream().map(ImgTag::getTagId).collect(Collectors.toSet()));
            imageVo.setTags(mapperFacade.mapAsList(tags, ImageVo.Tag.class));
        }
        return imageVo;
    }

    public Image add(ImageDto dto) {
        Image model = mapperFacade.map(dto, Image.class);
        repository.save(model);
        return find(model.getId());
    }

    public void del(Integer id) throws IOException {
        Image image = find(id);
        repository.deleteById(id);
        imgTagService.delByImgId(id);
        removeImage(image);
    }

    private boolean removeImage(Image image) throws IOException {
        Path path = Paths.get(image.getPath());
        return Files.deleteIfExists(path);
    }

    @Transactional
    public void update(ImageDto dto) {
        Image model = repository.findById(dto.getId()).orElse(null);
        if (Objects.isNull(model)) {
            return;
        }
        // update tags
        imgTagService.delByImgId(model.getId());
        if (!CollectionUtils.isEmpty(dto.getTagIds())) {
            ImgTagDto imgTagDto = new ImgTagDto()
                    .setImgId(model.getId())
                    .setTagIds(dto.getTagIds());
            imgTagService.add(imgTagDto);
        }

        mapperFacade.map(dto, model);
        repository.save(model);
    }

}
