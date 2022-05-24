package com.s4timuen.wta_api.service;

import com.s4timuen.wta_api.entity.User;
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
}
