package com.s4timuen.oauthauthorizationserver.service;

import com.s4timuen.oauthauthorizationserver.entity.User;
import com.s4timuen.oauthauthorizationserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Custom UserDetailsService implementation.
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private static final int ENCRYPTION_COST_FACTOR = 12;

    private static final String USER_NOT_FOUND
            = "User not found.";

    @Autowired
    private UserRepository userRepository;

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
     * Load user details by username.
     *
     * @param userEmail Email address of a user.
     * @return Spring user object.
     * @throws UsernameNotFoundException Throw if user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(userEmail);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                user.get().isEnabled(),
                true,
                true,
                true,
                getAuthorities(List.of(user.get().getRoles()))
        );
    }

    /**
     * Get authorities of a user.
     *
     * @param roles Roles of a user.
     * @return List of SimpleGrantedAuthority.
     */
    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
