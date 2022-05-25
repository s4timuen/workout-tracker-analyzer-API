package com.s4timuen.wta_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration for web security.
 */
@EnableWebSecurity
public class WebSecurityConfig {

    private static final int ENCRYPTION_COST_FACTOR = 12;

    /**
     * Password encoder.
     *
     * @return Encoder with respective encryption cost factor.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCRYPTION_COST_FACTOR);
    }
}
