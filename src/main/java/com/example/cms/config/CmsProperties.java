package com.example.cms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CmsProperties {

    @Value("${cms.image.host}")
    private String imageHost;

    public String getFullUrl(String relativeUrl) {
        return String.format("%s%s", imageHost, relativeUrl);
    }
}
