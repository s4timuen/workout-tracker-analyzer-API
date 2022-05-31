package com.s4timuen.wta_api.service;

import com.s4timuen.wta_api.entity.User;
import com.s4timuen.wta_api.entity.VerificationToken;
import com.s4timuen.wta_api.model.UserModel;

/**
 * User service interface.
 */
public interface UserService {

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
    void sendVerificationTokenMail(User user, String url, String messageOption);
}
