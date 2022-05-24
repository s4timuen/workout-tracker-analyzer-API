package com.s4timuen.wta_api.service;

import com.s4timuen.wta_api.entity.UserEntity;
import com.s4timuen.wta_api.model.UserModel;

public interface UserService {

    UserEntity registerUser(UserModel userModel);
}
