package de.hsos.ooadss23.blogapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    RequireLoginInterceptor requireLoginInterceptor;
    public MvcConfig(RequireLoginInterceptor requireLoginInterceptor) {
        this.requireLoginInterceptor = requireLoginInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.requireLoginInterceptor).addPathPatterns("/blogs/{*}", "/artikel/{*}"); // Enthält auch /blogs, /artikel, etc.
    }
}
