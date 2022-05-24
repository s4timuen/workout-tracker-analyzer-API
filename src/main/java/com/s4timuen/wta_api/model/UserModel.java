package com.s4timuen.wta_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User model (request/response body).
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