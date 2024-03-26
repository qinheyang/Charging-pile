package com.sad.web.config;

import com.sad.web.constant.Constants;
import com.sad.web.constant.SystemConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + SadConfig.getProfile() + "/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
