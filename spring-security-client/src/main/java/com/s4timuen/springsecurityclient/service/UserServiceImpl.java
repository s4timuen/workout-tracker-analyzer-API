package com.s4timuen.springsecurityclient.service;

import com.s4timuen.springsecurityclient.entity.PasswordChangeToken;
import com.s4timuen.springsecurityclient.entity.PasswordResetToken;
import com.s4timuen.springsecurityclient.entity.User;
import com.s4timuen.springsecurityclient.entity.VerificationToken;
import com.s4timuen.springsecurityclient.model.UserModel;
import com.s4timuen.springsecurityclient.repository.PasswordChangeTokenRepository;
import com.s4timuen.springsecurityclient.repository.PasswordResetTokenRepository;
import com.s4timuen.springsecurityclient.repository.UserRepository;
import com.s4timuen.springsecurityclient.repository.VerificationTokenRepository;
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

    private static final String[] ROLES_USER = {"user"};

    private static final String MESSAGE_TOKEN_VALIDATION_SUCCESS
            = "Registration verification token validation successful.";
    private static final String MESSAGE_TOKEN_VALIDATION_FAIL_TOKEN_INVALID
            = "Registration verification token failed, token not available.";
    private static final String MESSAGE_TOKEN_VALIDATION_FAIL_TOKEN_EXPIRED
            = "Registration verification token failed, token expired.";
    private static final String MESSAGE_TOKEN_PASSWORD_RESET_FAIL_TOKEN_INVALID
            = "Registration password reset token failed, token not available.";
    private static final String MESSAGE_TOKEN_PASSWORD_RESET_FAIL_TOKEN_EXPIRED
            = "Registration password reset token failed, token expired.";
    private static final String MESSAGE_TOKEN_PASSWORD_RESET_SUCCESS
            = "Registration password reset token validation successful.";
    private static final String MESSAGE_TOKEN_PASSWORD_CHANGE_FAIL_TOKEN_INVALID
            = "Registration password change token failed, token not available.";
    private static final String MESSAGE_TOKEN_PASSWORD_CHANGE_FAIL_TOKEN_EXPIRED
            = "Registration password change token failed, token expired.";
    private static final String MESSAGE_TOKEN_PASSWORD_CHANGE_SUCCESS
            = "Registration password change token validation successful.";
    private static final String MAIL_INVALID_MESSAGE_OPTION
            = "Invalid option for mail message.";
    private static final String MESSAGE_USER_NOT_FOUND
            = "No user found for the respective email address.";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private PasswordChangeTokenRepository passwordChangeTokenRepository;
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
    @Override
    public User registerUser(UserModel userModel) {

        User user = new User();
        user.setNickname(userModel.getNickname());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRoles(ROLES_USER);

        userRepository.save(user);
        return user;
    }

    /**
     * Save a verification token for a user registration.
     *
     * @param user  A user object.
     * @param token Verification token.
     */
    @Override
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
    @Override
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
        userRepository.save(user);
        return MESSAGE_TOKEN_VALIDATION_SUCCESS;
    }

    /**
     * Validate a password reset token.
     * If valid, enable user.
     *
     * @param token Password reset token.
     * @return Message whether password reset token validation was successful or failed.
     */
    @Override
    public String validatePasswordResetToken(String token) {

        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken.isEmpty()) {
            return MESSAGE_TOKEN_PASSWORD_RESET_FAIL_TOKEN_INVALID;
        }

        User user = passwordResetToken.get().getUser();
        Calendar calendar = Calendar.getInstance();

        if (passwordResetToken.get().getExpirationDate().getTime()
                - calendar.getTime().getTime() <= 0) {

            passwordResetTokenRepository.delete(passwordResetToken.get());
            return MESSAGE_TOKEN_PASSWORD_RESET_FAIL_TOKEN_EXPIRED;
        }

        return MESSAGE_TOKEN_PASSWORD_RESET_SUCCESS;
    }

    /**
     * Validate a password change token.
     * If valid, enable user.
     *
     * @param token Password change token.
     * @return Message whether password change token validation was successful or failed.
     */
    @Override
    public String validatePasswordChangeToken(String token) {

        Optional<PasswordChangeToken> passwordChangeToken = passwordChangeTokenRepository.findByToken(token);

        if (passwordChangeToken.isEmpty()) {
            return MESSAGE_TOKEN_PASSWORD_CHANGE_FAIL_TOKEN_INVALID;
        }

        User user = passwordChangeToken.get().getUser();
        Calendar calendar = Calendar.getInstance();

        if (passwordChangeToken.get().getExpirationDate().getTime()
                - calendar.getTime().getTime() <= 0) {

            passwordChangeTokenRepository.delete(passwordChangeToken.get());
            return MESSAGE_TOKEN_PASSWORD_CHANGE_FAIL_TOKEN_EXPIRED;
        }

        return MESSAGE_TOKEN_PASSWORD_CHANGE_SUCCESS;
    }

    /**
     * Generate a new token, whether the old one is already expired or not.
     *
     * @param oldToken Expired verification token.
     * @return A new verification token.
     */
    @Override
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
    @Override
    public void sendTokenMail(User user, String url, MessageOption messageOption) {

        switch (messageOption) {
            case SEND_VERIFICATION ->
                // TODO: send email with respective message, instead of console
                    log.info("Click the link to verify your account. {}", url);
            case RESEND_VERIFICATION ->
                // TODO: send email with respective message, instead of console
                    log.info("Click the new link to verify your account. {}", url);
            case RESET_PASSWORD ->
                // TODO: send email with respective message, instead of console
                    log.info("Click the link to get a new password. {}", url);
            case CHANGE_PASSWORD ->
                // TODO: send email with respective message, instead of console
                    log.info("Click the link to change your password. {}", url);
            default -> throw new IllegalStateException(MAIL_INVALID_MESSAGE_OPTION);
        }
    }

    /**
     * Generate and save a password reset token for a user.
     *
     * @param user A user object.
     */
    @Override
    public PasswordResetToken generatePasswordResetToken(User user) {

        PasswordResetToken passwordResetToken = new PasswordResetToken(user,
                UUID.randomUUID().toString());
        passwordResetTokenRepository.save(passwordResetToken);

        return passwordResetToken;
    }

    /**
     * Generate and save a password change token for a user.
     *
     * @param user A user object.
     */
    @Override
    public PasswordChangeToken generatePasswordChangeToken(User user) {

        PasswordChangeToken passwordChangeToken = new PasswordChangeToken(user,
                UUID.randomUUID().toString());
        passwordChangeTokenRepository.save(passwordChangeToken);

        return passwordChangeToken;
    }

    /**
     * Change a user password.
     *
     * @param user A user object.
     * @param newPassword A new password.
     */
    @Override
    public void changePassword(User user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Check if the old password is valid.
     *
     * @param user A user object.
     * @param oldPassword The old password to validate.
     */
    @Override
    public boolean checkOldPasswordValid(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    /**
     * Find a user by the respective email.
     *
     * @param email An email address.
     * @return A user object.
     */
    @Override
    public User getUserByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new IllegalStateException(MESSAGE_USER_NOT_FOUND);
        }
        return user.get();
    }

    /**
     * Get a user by the respective reset token.
     *
     * @param token A reset token.
     * @return A user object.
     */
    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {

        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).get().getUser());
    }
}