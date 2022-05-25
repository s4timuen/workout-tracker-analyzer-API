package com.s4timuen.wta_api.event.listener;

import com.s4timuen.wta_api.entity.User;
import com.s4timuen.wta_api.event.UserRegistrationEvent;
import com.s4timuen.wta_api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Event listener for user registration.
 */
@Slf4j
@Component
public class UserRegistrationEventListener implements ApplicationListener<UserRegistrationEvent> {

    private static final String VERIFY_REGISTRATION = "/verifyRegistration?token=";

    @Autowired
    private UserService userService;

    /**
     * Create verification token with link.
     * Send the token and the link per email to the user.
     *
     * @param event UserRegistrationEvent.
     */
    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        String url = event.getApplicationUrl() + VERIFY_REGISTRATION + token;

        userService.saveVerificationTokenForUser(user, token);

        // TODO: send email, instead of console
        log.info("Click the link to verify your account. {}", url);
    }
}
