package com.example.cms.global;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Optional;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    /**
     * 拦截request和response并放到上下文中
     *
     * @param request  要拦截的request
     * @param response 要拦截的response
     * @param handler  spring 机制传递过来的
     * @return 不中断，继续执行，返回为true
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        try {
            printReq(request);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn(e.getMessage());
        }

        return true;
        // return super.preHandle(request, response, handler);
    }

    private void printReq(HttpServletRequest request) throws IOException {
        StringBuilder builder = new StringBuilder();
        appendLn(builder, "");
        appendLn(builder, "---------- REQUEST ----------")
                .appendLn(builder, String.format("REMOTE: %s", request.getRemoteHost()))
                .appendLn(builder, String.format("API: %s %s", request.getMethod(), request.getRequestURI()))
                .appendLn(builder, String.format("QUERY: %s", URLDecoder.decode(Optional.ofNullable(request.getQueryString()).orElse(""))));
        printHeaders(builder, request);
        printPost(builder, request);
        printRequestBody(builder, request);

        log.info("Request incoming: {}", builder.toString());
    }
    private RequestInterceptor appendLn(StringBuilder builder, String str) {
        String ln = "\n";
        builder.append(str).append(ln);
        return this;
    }
    private void printHeaders(StringBuilder builder, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        appendLn(builder, "---- HEADERS ----");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            appendLn(builder, String.format("%s: %s", headerName, request.getHeader(headerName)));
        }
    }
    private void printPost(StringBuilder builder, HttpServletRequest request) {
        if ("post".equals(request.getMethod().toLowerCase())) {
            appendLn(builder, "---- POST PARAMS ----");
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                appendLn(builder, String.format("%s=%s", paramName, request.getParameter(paramName)));
            }
        }
    }
    private void printRequestBody(StringBuilder builder, HttpServletRequest request) throws IOException {
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        try {
            String body = requestWrapper.getBody();
            if (StringUtils.hasLength(body)) {
                JSONObject jsonObject = JSON.parseObject(body);
                StringBuilder jsonBody = new StringBuilder();
                jsonObject.getInnerMap().forEach((key, obj) -> {
                    jsonBody.append(String.format("%s: %s\n", key, trim(obj.toString())));
                });
                appendLn(builder, "---- REQUEST BODY ----");
                appendLn(builder, jsonBody.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        appendLn(builder, "--------");
    }
    private String trim(String longStr) {
        int limit = 500;
        int len = longStr.length();
        if (len > limit) {
            return longStr.substring(0, 250) + "..." + longStr.substring(len-250, len);
        }
        return longStr;
    }

}
