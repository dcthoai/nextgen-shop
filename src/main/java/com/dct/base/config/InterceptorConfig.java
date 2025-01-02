package com.dct.base.config;

import com.dct.base.interceptor.BaseHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Used to register and configure Interceptor for HTTP requests in web applications
 * @author thoaidc
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final BaseHandlerInterceptor baseHandlerInterceptor; // A self-defined custom interceptor

    public InterceptorConfig(BaseHandlerInterceptor baseHandlerInterceptor) {
        this.baseHandlerInterceptor = baseHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register a BaseHandlerInterceptor to handle requests (HTTP requests) before they are passed to the Controllers
        registry.addInterceptor(baseHandlerInterceptor)
                // Ignore some paths that don't need to be processed, such as:
                // Static files (favicon.ico, /images/**)
                // Paths related to the login page (**/login/**)
                // Error or internationalization files (/error**, /i18n/**)
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
