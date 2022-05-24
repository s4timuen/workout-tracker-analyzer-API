package com.s4timuen.wta_api.service;

import com.s4timuen.wta_api.entity.UserEntity;
import com.s4timuen.wta_api.model.UserModel;
import com.s4timuen.wta_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final String ROLE_USER = "user";
    private final String ROLE_ADMIN = "admin";

    @Autowired
    private UserRepository userRepository;
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
    public UserEntity registerUser(UserModel userModel) {

        UserEntity user = new UserEntity();
        user.setNickname(userModel.getNickname());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(ROLE_USER);

        userRepository.save(user);
        return user;
    }
}
