package com.example.cms.global.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@Slf4j
// @Component
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    public RequestLoggingFilter() {
        setBeforeMessagePrefix("Request: ");
        setIncludePayload(true);
        setIncludeQueryString(true);
        setIncludeClientInfo(true);
        setIncludeHeaders(true);
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        log.info(message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {

    }
}
