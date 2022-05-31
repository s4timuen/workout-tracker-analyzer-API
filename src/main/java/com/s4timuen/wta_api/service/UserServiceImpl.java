package com.s4timuen.wta_api.service;

import com.s4timuen.wta_api.entity.User;
import com.s4timuen.wta_api.entity.VerificationToken;
import com.s4timuen.wta_api.model.UserModel;
import com.s4timuen.wta_api.repository.UserRepository;
import com.s4timuen.wta_api.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

/**
 * User service implementation.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "user";
    private static final String ROLE_ADMIN = "admin";

    private final static String MESSAGE_TOKEN_VALIDATION_SUCCESS
            = "Registration verification token validation successful.";
    private final static String MESSAGE_TOKEN_VALIDATION_FAIL_TOKEN_INVALID
            = "Registration verification token failed, token not available.";
    private final static String MESSAGE_TOKEN_VALIDATION_FAIL_TOKEN_EXPIRED
            = "Registration verification token failed, token expired.";

    private static final String MAIL_INVALID_MESSAGE_OPTION
            = "Invalid option for mail message.";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a user.
     * User is stored to the DB, with encrypted password.
     * Currently, the user role is always "user".
     *
     * @param userModel User data from request body.
     * @return A user object.
     */
    public User registerUser(UserModel userModel) {

        User user = new User();
        user.setNickname(userModel.getNickname());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(ROLE_USER);

        userRepository.save(user);
        return user;
    }

    /**
     * Save a verification token for a user registration.
     *
     * @param user  A user object.
     * @param token Verification token.
     */
    public void saveVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    /**
     * Validate a verification token for a user registration.
     * If valid, enable user.
     *
     * @param token Verification token.
     * @return Message whether verification token validation was successful or failed.
     */
    public String validateVerificationToken(String token) {

        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken.isEmpty()) {
            return MESSAGE_TOKEN_VALIDATION_FAIL_TOKEN_INVALID;
        }

        User user = verificationToken.get().getUser();
        Calendar calendar = Calendar.getInstance();

        if (verificationToken.get().getExpirationDate().getTime()
                - calendar.getTime().getTime() <= 0) {

            verificationTokenRepository.delete(verificationToken.get());
            return MESSAGE_TOKEN_VALIDATION_FAIL_TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        return MESSAGE_TOKEN_VALIDATION_SUCCESS;
    }

    /**
     * Generate a new token, whether the old one is already expired or not.
     *
     * @param oldToken Expired verification token.
     * @return A new verification token.
     */
    public VerificationToken generateNewVerificationToken(String oldToken) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken).get();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    /**
     * Send mail with verification token for user registration.
     *
     * @param user A user object.
     * @param url  Verification link for user to click.
     */
    public void sendVerificationTokenMail(User user, String url, String messageOption) {

        switch (messageOption.toLowerCase()) {
            case "send" ->
                // TODO: send email with respective message, instead of console
                    log.info("Click the link to verify your account. {}", url);
            case "resend" ->
                // TODO: send email with respective message, instead of console
                    log.info("Click the new link to verify your account. {}", url);
            default -> throw new IllegalStateException(MAIL_INVALID_MESSAGE_OPTION);
        }
    }
}
