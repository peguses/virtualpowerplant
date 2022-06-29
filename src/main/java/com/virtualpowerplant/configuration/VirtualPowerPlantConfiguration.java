package com.virtualpowerplant.configuration;

import com.virtualpowerplant.Interceptor.ApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class VirtualPowerPlantConfiguration implements  WebMvcConfigurer {

    @Autowired
    ApiInterceptor apiInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
