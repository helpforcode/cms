package com.example.cms.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.example.cms.dto.*;
import com.example.cms.storage.entity.*;
import com.example.cms.vo.ArticleAdminVo;
import com.example.cms.vo.ArticleVo;
import com.example.cms.vo.DailyWordVo;
import com.example.cms.vo.ImageVo;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class BeanMapperConfig implements OrikaMapperFactoryConfigurer {
    @Autowired
    private CmsProperties cmsProperties;

    @Override
    public void configure(MapperFactory factory) {

        factory.classMap(ArticleDto.class, Article.class)
                .customize(new CustomMapper<ArticleDto, Article>() {
                    @Override
                    public void mapAtoB(ArticleDto from, Article to, MappingContext context) {
                        to.setPublishedAt(DateUtil.parse(from.getPublishedAt(),
                                DatePattern.NORM_DATE_PATTERN, DatePattern.NORM_DATETIME_PATTERN, DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                        to.setImages(JSON.toJSONString(from.getImages()));
                    }

                })
                .exclude("publishedAt")
                .exclude("images")
                .mapNulls(false)
                .byDefault().register();

        factory.classMap(DailyWordDto.class, DailyWord.class)
                .customize(new CustomMapper<DailyWordDto, DailyWord>() {
                    @Override
                    public void mapAtoB(DailyWordDto from, DailyWord to, MappingContext context) {
                        if (Objects.nonNull(from.getPublishedAt())) {
                            to.setPublishedAt(DateUtil.parse(from.getPublishedAt(),
                                    DatePattern.NORM_DATE_PATTERN, DatePattern.NORM_DATETIME_PATTERN, DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                        }
                    }

                })
                .exclude("publishedAt")
                .mapNulls(false)
                .byDefault().register();

        factory.classMap(MenuDto.class, Menu.class)
                .customize(new CustomMapper<MenuDto, Menu>() {
                    @Override
                    public void mapAtoB(MenuDto from, Menu to, MappingContext context) {
                    }

                })
                .mapNulls(false)
                .byDefault().register();

        factory.classMap(ImageDto.class, Image.class)
                .customize(new CustomMapper<ImageDto, Image>() {
                    @Override
                    public void mapAtoB(ImageDto from, Image to, MappingContext context) {
                    }

                })
                .mapNulls(false)
                .byDefault().register();

        factory.classMap(Image.class, ImageVo.class)
                .customize(new CustomMapper<Image, ImageVo>() {
                    @Override
                    public void mapAtoB(Image from, ImageVo to, MappingContext context) {
                        to.setRelativeUrl(from.getUrl());
                        to.setUrl(cmsProperties.getFullUrl(from.getUrl()));
                    }
                })
                .mapNulls(false)
                .exclude("url")
                .byDefault().register();

        factory.classMap(Article.class, ArticleVo.class)
                .customize(new CustomMapper<Article, ArticleVo>() {
                    @Override
                    public void mapAtoB(Article from, ArticleVo to, MappingContext context) {
                        String images = from.getImages();
                        List<String> fullUrls = new ArrayList<>();
                        if (StringUtils.hasLength(images)) {
                            try {
                                List<String> relativeUrls = JSON.parseArray(images, String.class);
                                fullUrls = relativeUrls.stream().map(url -> cmsProperties.getFullUrl(url)).collect(Collectors.toList());
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        }
                        to.setImages(fullUrls);
                        to.setPublishedAt(DateUtil.format(from.getPublishedAt(), DatePattern.NORM_DATE_PATTERN));
                    }
                })
                .mapNulls(false)
                .exclude("images")
                .exclude("publishedAt")
                .byDefault().register();

        factory.classMap(Article.class, ArticleAdminVo.class)
                .customize(new CustomMapper<Article, ArticleAdminVo>() {
                    @Override
                    public void mapAtoB(Article from, ArticleAdminVo to, MappingContext context) {
                        String images = from.getImages();
                        List<String> relativeUrls = new ArrayList<>();
                        if (StringUtils.hasLength(images)) {
                            try {
                                relativeUrls = JSON.parseArray(images, String.class);
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        }
                        to.setImages(relativeUrls);
                        to.setPublishedAt(DateUtil.format(from.getPublishedAt(), DatePattern.NORM_DATE_PATTERN));
                    }
                })
                .mapNulls(false)
                .exclude("images")
                .exclude("publishedAt")
                .byDefault().register();


        factory.classMap(DailyWord.class, DailyWordVo.class)
                .customize(new CustomMapper<DailyWord, DailyWordVo>() {
                    @Override
                    public void mapAtoB(DailyWord from, DailyWordVo to, MappingContext context) {
                        to.setCode(StrUtil.fillBefore(from.getCode().toString(), '0', 3));
                    }
                })
                .exclude("code")
                .exclude("words")
                .mapNulls(false)
                .byDefault().register();

        factory.classMap(InfoDto.class, Info.class)
                .customize(new CustomMapper<InfoDto, Info>() {
                    @Override
                    public void mapAtoB(InfoDto from, Info to, MappingContext context) {
                    }
                })
                .mapNulls(false)
                .byDefault().register();
    }
}
