package com.s4timuen.springsecurityclient.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Verification token for the user registration.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

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
     * Constructor for class VerificationToken.
     *
     * @param user  User object.
     * @param token Verification token.
     */
    public VerificationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expirationDate = calculateExpirationDate(EXPIRATION_TIME);
    }

    /**
     * Calculate the date and time, when the token expires.
     *
     * @param expirationTime How long the token is valid, in minutes.
     * @return Date and time, when the token expires.
     */
    @SuppressWarnings("SameParameterValue")
    private Date calculateExpirationDate(int expirationTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);

        return new Date(calendar.getTime().getTime());
    }
}