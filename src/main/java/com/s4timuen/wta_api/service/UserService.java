package com.s4timuen.wta_api.service;

import com.s4timuen.wta_api.entity.User;
import com.s4timuen.wta_api.model.UserModel;

public interface UserService {

    User registerUser(UserModel userModel);
}
