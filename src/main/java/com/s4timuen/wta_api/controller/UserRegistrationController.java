package com.s4timuen.wta_api.controller;

import com.s4timuen.wta_api.entity.User;
import com.s4timuen.wta_api.event.UserRegistrationEvent;
import com.s4timuen.wta_api.model.UserModel;
import com.s4timuen.wta_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for user registration.
 */
@RestController
@RequestMapping(path = "api/v1/register")
public class UserRegistrationController {

    private static final String MESSAGE_USER_REGISTRATION_SUCCESS = "User registration successful.";

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * User registration (POST).
     *
     * @param userModel User data from request body.
     * @return Success message.
     */
    @PostMapping("/user")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerUser(userModel);
        eventPublisher.publishEvent(new UserRegistrationEvent(user, buildApplicationUrl(request)));
        return MESSAGE_USER_REGISTRATION_SUCCESS;
    }

    /**
     * Build the application URL.
     *
     * @param request From request body.
     * @return Application URL.
     */
    private String buildApplicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
