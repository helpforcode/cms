package com.example.cms.global;

import com.example.cms.global.dto.PageInfo;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@Order(0)
public class PageableResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType() == PageImpl.class;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!(body instanceof Page)) {
            return body;
        }
        PageInfo<Object> pageInfo = new PageInfo<>();
        Page p = (Page) body;
        pageInfo.setContent(p.getContent());

        PageInfo.Info info = new PageInfo.Info();
        info.setPage(p.getNumber() + 1);
        info.setTotalPages(p.getTotalPages());
        info.setTotalElements(p.getTotalElements());
        info.setNumberOfElements(p.getNumberOfElements());
        info.setPageSize(p.getSize());

        pageInfo.setPageInfo(info);
        return pageInfo;
    }
}
