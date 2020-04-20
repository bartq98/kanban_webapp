package com.example.kanban;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("layouts/home");  // todo: change to fragments/home/mainpage
        registry.addViewController("/").setViewName("layouts/home");
        registry.addViewController("/login").setViewName("fragments/forms/login");
        registry.addViewController("/info").setViewName("fragments/userprofile");
        registry.addViewController("/forgot-password").setViewName("fragments/forms/forgot-password");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
    }
}