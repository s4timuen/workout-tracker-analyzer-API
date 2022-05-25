package com.s4timuen.wta_api.service;

import com.s4timuen.wta_api.entity.User;
import com.s4timuen.wta_api.entity.VerificationToken;
import com.s4timuen.wta_api.model.UserModel;
import com.s4timuen.wta_api.repository.UserRepository;
import com.s4timuen.wta_api.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User service implementation.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "user";
    private static final String ROLE_ADMIN = "admin";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
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
    public User registerUser(UserModel userModel) {

        User user = new User();
        user.setNickname(userModel.getNickname());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(ROLE_USER);

        userRepository.save(user);
        return user;
    }

    /**
     * Save a verification token for a user.
     *
     * @param user  A user object.
     * @param token Verification token.
     */
    public void saveVerificationTokenForUser(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }
}
