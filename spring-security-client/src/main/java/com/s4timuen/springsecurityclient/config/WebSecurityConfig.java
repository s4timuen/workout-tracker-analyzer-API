package com.s4timuen.springsecurityclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration for web security.
 */
@EnableWebSecurity
public class WebSecurityConfig {

    private static final int ENCRYPTION_COST_FACTOR = 12;

    private static final String[] WHITELIST_URLS = {
            "/registerUser",
            "/verifyRegistration",
            "/resendVerificationToken",
            "/resetPassword",
            "/changePassword"
    };

    /**
     * Password encoder.
     *
     * @return Encoder with respective encryption cost factor.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(ENCRYPTION_COST_FACTOR);
    }

    /**
     * Filter chain for http security and urls whitelist.
     *
     * @return Http security configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(WHITELIST_URLS)
                .permitAll()
                .antMatchers("/api/v1/**")
                .authenticated()
                .and()
                .oauth2Login(oauth2login ->
                        oauth2login.loginPage("/oauth2/authorization/api-client-oidc"))
                .oauth2Client(Customizer.withDefaults());

        return http.build();
    }
}