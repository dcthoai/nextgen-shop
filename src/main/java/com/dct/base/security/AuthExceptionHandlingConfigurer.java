package com.dct.base.security;

import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

@Component
public class AuthExceptionHandlingConfigurer<T extends HttpSecurityBuilder<T>> extends AbstractHttpConfigurer<AuthExceptionHandlingConfigurer<T>, T> {

}
