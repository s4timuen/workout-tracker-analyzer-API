package com.s4timuen.springsecurityclient.controller;

import com.s4timuen.springsecurityclient.entity.PasswordResetToken;
import com.s4timuen.springsecurityclient.entity.User;
import com.s4timuen.springsecurityclient.entity.VerificationToken;
import com.s4timuen.springsecurityclient.event.UserRegistrationEvent;
import com.s4timuen.springsecurityclient.model.PasswordModel;
import com.s4timuen.springsecurityclient.model.UserModel;
import com.s4timuen.springsecurityclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Controller for user registration.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/api/v1")
public class UserRegistrationController {

    private static final String RESEND_VERIFY_REGISTRATION = "/resendVerificationToken?token=";
    private static final String RESET_PASSWORD = "/resetPassword?token=";

    private static final String MESSAGE_USER_REGISTRATION_SUCCESS
            = "User registration successful.";
    private static final String MESSAGE_REGISTRATION_VERIFICATION_SUCCESS
            = "Registration verification successful.";
    private static final String MESSAGE_REGISTRATION_VERIFICATION_FAIL
            = "Registration verification token validation failed.";
    private static final String MESSAGE_TOKEN_VALIDATION_SUCCESS
            = "Registration verification token validation successful.";
    private static final String MESSAGE_TOKEN_PASSWORD_RESET_FAIL
            = "Password reset token validation failed.";
    private static final String MESSAGE_TOKEN_PASSWORD_RESET_SUCCESS
            = "Password reset token validation successful.";
    private static final String MESSAGE_VERIFICATION_MAIL_RESEND
            = "Mail with user registration validation token has been resend.";
    private static final String MESSAGE_RESET_PASSWORD_MAIL_SEND
            = "Mail with user reset password token has been send.";
    private static final String MESSAGE_CHANGE_PASSWORD_SUCCESS
            = "Password has been successfully changed.";
    private static final String MESSAGE_OLD_PASSWORD_INVALID
            = "Old Password is invalid.";

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * User registration.
     *
     * @param userModel User data.
     * @param request   From request body.
     * @return Success message.
     */
    @PostMapping(path = "/registerUser")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {

        User user = userService.registerUser(userModel);
        eventPublisher.publishEvent(new UserRegistrationEvent(user, buildApplicationUrl(request)));

        return MESSAGE_USER_REGISTRATION_SUCCESS;
    }

    /**
     * User registration verification.
     *
     * @param token Verification token from request parameter.
     * @return Message whether verification was successful or failed.
     */
    @GetMapping(path = "/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {

        String result = userService.validateVerificationToken(token);

        if (result.equalsIgnoreCase(MESSAGE_TOKEN_VALIDATION_SUCCESS)) {
            return MESSAGE_REGISTRATION_VERIFICATION_SUCCESS;
        }
        return MESSAGE_REGISTRATION_VERIFICATION_FAIL;
    }

    /**
     * Resend user registration verification token.
     *
     * @param oldToken Initial or last generated token, may be expired.
     * @param request  From request body.
     * @return Message that a new verification token has been sent.
     */
    @GetMapping(path = "/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          final HttpServletRequest request) {

        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();

        userService.sendTokenMail(
                user,
                buildApplicationUrl(request) + RESEND_VERIFY_REGISTRATION + verificationToken,
                UserService.MessageOption.RESEND_VERIFICATION);

        return MESSAGE_VERIFICATION_MAIL_RESEND;
    }

    /**
     * Send user password reset token.
     *
     * @param passwordModel Password data.
     * @param request       From request body.
     * @return Message that a reset password token has been sent.
     */
    @PostMapping(path = "/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel,
                                final HttpServletRequest request) {

        User user = userService.getUserByEmail(passwordModel.getEmail());
        PasswordResetToken passwordResetToken = userService.generatePasswordResetToken(user);
        userService.sendTokenMail(
                user,
                buildApplicationUrl(request) + RESET_PASSWORD + passwordResetToken,
                UserService.MessageOption.RESET_PASSWORD);

        return MESSAGE_RESET_PASSWORD_MAIL_SEND;
    }

    @PostMapping(path = "/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) {

        User user = userService.getUserByEmail(passwordModel.getEmail());

        if(!userService.checkOldPasswordValid(user, passwordModel.getOldPassword())) {
            return MESSAGE_OLD_PASSWORD_INVALID;
        }

        userService.changePassword(user, passwordModel.getNewPassword());
        return MESSAGE_CHANGE_PASSWORD_SUCCESS;
    }

    @PostMapping(path = "/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel) {

        String result = userService.validatePasswordResetToken(token);

        if (!result.equalsIgnoreCase(MESSAGE_TOKEN_PASSWORD_RESET_SUCCESS)) {
            return MESSAGE_TOKEN_PASSWORD_RESET_FAIL;
        }

        Optional<User> user = userService.getUserByPasswordResetToken(token);

        if(user.isEmpty()) {
            return MESSAGE_TOKEN_PASSWORD_RESET_FAIL;
        }

        userService.changePassword(user.get(), passwordModel.getNewPassword());
        return MESSAGE_TOKEN_PASSWORD_RESET_SUCCESS;
    }

    /**
     * Build the application URL.
     *
     * @param request From request body.
     * @return Application URL.
     */
    private String buildApplicationUrl(HttpServletRequest request) {

        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + "/api/v1";
    }
}