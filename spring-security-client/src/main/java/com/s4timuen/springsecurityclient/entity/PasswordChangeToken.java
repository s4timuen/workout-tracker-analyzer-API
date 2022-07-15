package com.s4timuen.springsecurityclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * User password change token.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_change_tokens")
public class PasswordChangeToken {


    private static final int EXPIRATION_TIME = 30;  // 30min

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "verification_token", nullable = false)
    private String token;
    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    /**
     * Constructor for class PasswordChangeToken.
     *
     * @param user  User object.
     * @param token Password change token.
     */
    public PasswordChangeToken(User user, String token) {
        super();
        this.user = user;
        this.token = token;
        this.expirationDate = calculateExpirationDate(EXPIRATION_TIME);
    }

    /**
     * Constructor for class PasswordChangeToken.
     *
     * @param token Password change token.
     */
    public PasswordChangeToken(String token) {
        super();
        this.token = token;
        this.expirationDate = calculateExpirationDate(EXPIRATION_TIME);
    }

    /**
     * Calculate the date and time, when the token expires.
     *
     * @param expirationTime How long the token is valid, in minutes.
     * @return Date and time, when the token expires.
     */
    private Date calculateExpirationDate(int expirationTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);

        return new Date(calendar.getTime().getTime());
    }
}