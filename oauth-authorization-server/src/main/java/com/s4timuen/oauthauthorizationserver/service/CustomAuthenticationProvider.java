package com.s4timuen.oauthauthorizationserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final String ERROR_BAD_CREDENTIALS
            = "Bad credentials/Unregistered user/Unverified user.";

    @Autowired
    @SuppressWarnings("unused")
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    @SuppressWarnings("unused")
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticate a user.
     *
     * @param authentication An authentication object.
     * @return An authentication object.
     * @throws AuthenticationException If authentication was not successful.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails user = customUserDetailsService.loadUserByUsername(username);

        return checkUserPassword(user, password);
    }

    /**
     * Check whether indicated authentication is supported or not.
     *
     * @param authentication Indicated authentication.
     * @return True if indicated authentication is supported.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * Check user credentials.
     *
     * @param user        A user object.
     * @param rawPassword Password from authentication credentials.
     * @return A user password authentication token.
     * @throws BadCredentialsException If user credentials do not match.
     */
    private Authentication checkUserPassword(UserDetails user, String rawPassword) {
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
            );
        }
        throw new BadCredentialsException(ERROR_BAD_CREDENTIALS);
    }
}
