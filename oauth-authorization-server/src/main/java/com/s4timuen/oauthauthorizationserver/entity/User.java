package com.s4timuen.oauthauthorizationserver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @ElementCollection
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles", nullable = false)
    private List<String> roles;
    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;
}
