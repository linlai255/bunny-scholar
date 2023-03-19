package com.linlai.bunnyscholar.config;


import com.linlai.bunnyscholar.interceptor.LoginInterceptor;
import com.linlai.bunnyscholar.interceptor.RecordSubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private RecordSubmitInterceptor recordSubmitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/login/**").order(1);
        registry.addInterceptor(recordSubmitInterceptor).addPathPatterns("/api/record/**").order(99);
    }

}
