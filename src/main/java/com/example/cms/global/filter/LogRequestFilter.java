package com.example.cms.global.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.cms.global.HttpRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Optional;

@Slf4j
@Component
public class LogRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        StringBuilder builder = new StringBuilder();

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        printReq(builder, httpRequest);

        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(httpRequest);

        printRequestBody(builder, requestWrapper);


        log.info("\n{}", builder);

        chain.doFilter(requestWrapper, response);
    }

    private StringBuilder printReq(StringBuilder builder, HttpServletRequest request) {
        String queryString = Optional.ofNullable(request.getQueryString()).orElse("");
        appendLn(builder, "------ Request Incoming ------")
                .appendLn(builder, String.format("REMOTE: %s", request.getRemoteHost()))
                .appendLn(builder, String.format("API: %s %s%s"
                        , request.getMethod()
                        , request.getRequestURI()
                        , StringUtils.hasLength(queryString) ? String.format("?%s", queryString) : ""
                ));
        printHeaders(builder, request);
        printPost(builder, request);
        return builder;
    }

    private LogRequestFilter appendLn(StringBuilder builder, String str) {
        String ln = "\n";
        builder.append(str).append(ln);
        return this;
    }

    private void printHeaders(StringBuilder builder, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        appendLn(builder, "---- HEADERS ----");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            appendLn(builder, String.format("%s: %s", headerName, ellipsis(request.getHeader(headerName))));
        }
    }
    private void printPost(StringBuilder builder, HttpServletRequest request) {
        if ("post".equals(request.getMethod().toLowerCase())) {
            appendLn(builder, "---- POST PARAMS ----");
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                appendLn(builder, String.format("%s: %s", paramName, request.getParameter(paramName)));
            }
        }
    }

    private StringBuilder printRequestBody(StringBuilder builder, HttpRequestWrapper requestWrapper) {
        try {
            String body = requestWrapper.getBody();
            if (StringUtils.hasLength(body)) {
                appendLn(builder, "---- REQUEST BODY ----");
                JSONObject jsonObject = JSON.parseObject(body);
                jsonObject.getInnerMap().forEach((key, obj) -> {
                    appendLn(builder, String.format("%s: %s", key, ellipsis(obj.toString())));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        appendLn(builder, "----------------------------");
        return builder;
    }
    private String ellipsis(String longStr) {
        int limit = 500;
        int len = longStr.length();
        if (len > limit) {
            return longStr.substring(0, 250) + "..." + longStr.substring(len-250, len);
        }
        return longStr;
    }

}
