package com.s4timuen.wta_api.service;

import com.s4timuen.wta_api.entity.PasswordChangeToken;
import com.s4timuen.wta_api.entity.PasswordResetToken;
import com.s4timuen.wta_api.entity.User;
import com.s4timuen.wta_api.entity.VerificationToken;
import com.s4timuen.wta_api.model.PasswordModel;
import com.s4timuen.wta_api.model.UserModel;

import java.util.Optional;

/**
 * User service interface.
 */
public interface UserService {

    /**
     * Options for mail messages.
     */
    enum MessageOption {
        SEND_VERIFICATION,
        RESEND_VERIFICATION,
        RESET_PASSWORD,
        CHANGE_PASSWORD
    }

    /**
     * Register a user.
     *
     * @param userModel User data from request body.
     * @return A user object.
     */
    User registerUser(UserModel userModel);

    /**
     * Save a verification token for a user registration.
     *
     * @param user  A user object.
     * @param token Verification token.
     */
    void saveVerificationTokenForUser(User user, String token);

    /**
     * Validate a verification token for a user registration.
     *
     * @param token Verification token.
     * @return Message whether verification token validation was successful or failed.
     */
    String validateVerificationToken(String token);

    /**
     * Validate a password reset token.
     *
     * @param token Password reset token.
     * @return Message whether password reset token validation was successful or failed.
     */
    String validatePasswordResetToken(String token);

    /**
     * Validate a password change token.
     *
     * @param token Password change token.
     * @return Message whether password change token validation was successful or failed.
     */
    String validatePasswordChangeToken(String token);

    /**
     * Generate a new token, whether the old one is already expired or not.
     *
     * @param oldToken Expired verification token.
     * @return A new verification token.
     */
    VerificationToken generateNewVerificationToken(String oldToken);

    /**
     * Send mail with verification token for user registration.
     *
     * @param user          A user object.
     * @param url           Verification link for user to click.
     * @param messageOption Different mail messages for user (first verification token or new token).
     */
    void sendTokenMail(User user, String url, UserService.MessageOption messageOption);

    /**
     * Generate and save a password reset token for a user.
     *
     * @param user A user object.
     */
    PasswordResetToken generatePasswordResetToken(User user);

    /**
     * Generate and save a password change token for a user.
     *
     * @param user A user object.
     */
    PasswordChangeToken generatePasswordChangeToken(User user);

    /**
     * Change a user password.
     *
     * @param user A user object.
     * @param newPassword A new password.
     */
    void changePassword(User user, String newPassword);

    /**
     * Check if the old password is valid.
     *
     * @param user A user object.
     * @param oldPassword The old password to validate.
     */
    boolean checkOldPasswordValid(User user, String oldPassword);

    /**
     * Get a user by the respective email.
     *
     * @param email An email address.
     * @return A user object.
     */
    User getUserByEmail(String email);

    /**
     * Get a user by the respective reset token.
     *
     * @param token A reset token.
     * @return A user object.
     */
    Optional<User> getUserByPasswordResetToken(String token);
}
