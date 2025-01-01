package com.dct.base.common;

import com.dct.base.constants.BaseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class BaseCommon {

    private static final Logger log = LoggerFactory.getLogger(BaseCommon.class);
    private final MessageSource messageSource;

    public BaseCommon(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessageI18n(String messageKey, Object ...args) {
        log.info("Translate message for {}", messageKey);
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, args, BaseConstants.TRANSLATE_NOT_FOUND, locale);
    }
}
