package com.s4timuen.wta_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for user password information (request/response body).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordModel {

    private String email;
}
