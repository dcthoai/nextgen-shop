package com.dct.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * In Spring, {@link LocaleResolver} determines the current language of the application based on the HTTP request. <p>
 * {@link AcceptHeaderLocaleResolver} automatically analyzes the value of the Accept-Language header in each request
 * and selects the appropriate language.
 */
@Configuration
public class LocaleConfiguration {

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }
}
