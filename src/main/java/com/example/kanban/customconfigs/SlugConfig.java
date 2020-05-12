package com.example.kanban.customconfigs;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SlugProperties.class)
public class SlugConfig {

    private SlugProperties slugProperties;

    public SlugProperties getSlugProperties() {
        return slugProperties;
    }
}
