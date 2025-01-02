package com.dct.base.config;

import com.dct.base.constants.BaseConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Helps JPA automatically handle annotations like @CreatedBy, @LastModifiedBy,... in entities
 * @author thoaidc
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class PersistenceConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            // Get the current username from the SecurityContext, using a default value if no user is authenticated
            String creator = SecurityContextHolder.getContext().getAuthentication().getName();
            return Optional.of(Optional.ofNullable(creator).orElse(BaseConstants.DEFAULT_CREATOR));
        };
    }
}
