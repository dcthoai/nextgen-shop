package com.dct.base.config;

import com.dct.base.interceptor.BaseHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final BaseHandlerInterceptor baseHandlerInterceptor;

    public InterceptorConfig(BaseHandlerInterceptor baseHandlerInterceptor) {
        this.baseHandlerInterceptor = baseHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baseHandlerInterceptor)
                .excludePathPatterns("**/favicon.ico")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("**images**")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("**index.html**")
                .excludePathPatterns("**/file/**")
                .excludePathPatterns("**/login/**")
                .excludePathPatterns("/error**")
                .excludePathPatterns("/i18n/**");
    }
}
