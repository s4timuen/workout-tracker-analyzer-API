package com.s4timuen.wta_api.event;

import com.s4timuen.wta_api.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * Event for user registration.
 */
@Getter
@Setter
public class UserRegistrationEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    /**
     * Constructor for class UserRegistrationEvent.
     *
     * @param user           User object.
     * @param applicationUrl The application URL.
     */
    public UserRegistrationEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
