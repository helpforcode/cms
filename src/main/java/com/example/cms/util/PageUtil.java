package com.example.cms.util;

import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class PageUtil {

    @Resource
    private MapperFacade mapperFacade;

    /**
     *
     * 将Page<源对象>转换为Page<目标对象>
     * @param sourcePage Page&lt;Source&gt;
     * @param <S> type of source object
     * @param <D> type of destination object
     * @return Page&lt;Destination&gt;
     */
    public <S, D> Page<D> toPage(
            Page<S> sourcePage
            , Class<D> destinationClass
    ) {

        List<D> destinationList = mapperFacade.mapAsList(sourcePage.getContent(), destinationClass);
        return new PageImpl<>(destinationList, sourcePage.getPageable(), sourcePage.getTotalElements());
    }

    public <S, D> Page<D> listToPage(
            List<D> destinationList
            , Page<S> sourcePage) {
        return new PageImpl<>(destinationList, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
