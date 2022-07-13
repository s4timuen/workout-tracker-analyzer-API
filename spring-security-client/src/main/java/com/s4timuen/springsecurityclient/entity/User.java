package com.s4timuen.springsecurityclient.entity;

import lombok.*;

import javax.persistence.*;

/**
 * User entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    private static final int MAX_PW_SIZE = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "first_name") // default: nullable = true
    private String firstName;
    @Column(name = "last_name") // default: nullable = true
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false, length = MAX_PW_SIZE)
    private String password;
    @Column(name = "roles", nullable = false)
    private String[] roles;
    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;
}