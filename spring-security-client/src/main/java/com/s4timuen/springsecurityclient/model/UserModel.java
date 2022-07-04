package com.s4timuen.springsecurityclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for user data (request/response body).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private String nickname;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String matchingPassword;
}