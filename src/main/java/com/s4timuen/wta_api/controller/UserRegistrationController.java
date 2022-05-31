package com.s4timuen.wta_api.controller;

import com.s4timuen.wta_api.entity.User;
import com.s4timuen.wta_api.entity.VerificationToken;
import com.s4timuen.wta_api.event.UserRegistrationEvent;
import com.s4timuen.wta_api.model.UserModel;
import com.s4timuen.wta_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for user registration.
 */
@RestController
@RequestMapping(path = "/api/v1")
public class UserRegistrationController {

    private static final String MESSAGE_USER_REGISTRATION_SUCCESS
            = "User registration successful.";
    private static final String MESSAGE_REGISTRATION_VERIFICATION_SUCCESS
            = "Registration verification successful.";
    private static final String MESSAGE_REGISTRATION_VERIFICATION_FAIL
            = "Registration verification failed.";
    private final static String MESSAGE_TOKEN_VALIDATION_SUCCESS
            = "Registration verification token validation successful.";
    private static final String MESSAGE_VERIFICATION_MAIL_RESEND
            = "Mail with user registration validation token has been resend.";

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * User registration.
     *
     * @param userModel User data from request body.
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
     */
    @GetMapping(path = "/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          final HttpServletRequest request) {

        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();

        userService.sendVerificationTokenMail(user, buildApplicationUrl(request), "resend");

        return MESSAGE_VERIFICATION_MAIL_RESEND;
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
