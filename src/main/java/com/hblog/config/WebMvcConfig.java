package com.hblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
/*        registry
            .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/static/webjars/", "classpath:/META-INF/resources/webjars/");*/
        registry
            .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/META-INF/resources/");
    }
}