package com.jxqixin.trafic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Value("${uploadFile.urlMapping}")
    private String urlMapping;
    @Value("${uploadFile.resourceLocation}")
    private String resourceLocation;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(urlMapping).addResourceLocations("file:"+resourceLocation);
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/","classpath:/resources/",
                        "classpath:/public/");
        super.addResourceHandlers(registry);
    }
}
