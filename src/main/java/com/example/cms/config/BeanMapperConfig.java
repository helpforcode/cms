package com.example.cms.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.example.cms.dto.ArticleDto;
import com.example.cms.dto.DailyWordDto;
import com.example.cms.dto.ImageDto;
import com.example.cms.dto.MenuDto;
import com.example.cms.storage.entity.Article;
import com.example.cms.storage.entity.DailyWord;
import com.example.cms.storage.entity.Image;
import com.example.cms.storage.entity.Menu;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanMapperConfig implements OrikaMapperFactoryConfigurer {

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
                        to.setPublishedAt(DateUtil.parse(from.getPublishedAt(),
                                DatePattern.NORM_DATE_PATTERN, DatePattern.NORM_DATETIME_PATTERN));
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
    }
}
