package com.s4timuen.springsecurityclient.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Password reset token tests.
 */
@SpringBootTest
@DisplayName("Password Reset Token")
public class PasswordResetTokenTest {

    Long id = 1L;
    User user = new User(
            1L,
            "nick",
            "first",
            "last",
            "first.last@gmail.com",
            "123",
            List.of("user"),
            false
    );
    String token;
    Date expirationDate;
    PasswordResetToken passwordToken;

    @BeforeEach
    public void setUp() {
        token = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        expirationDate = new Date(calendar.getTime().getTime());
        passwordToken = new PasswordResetToken(
                id,
                user,
                token,
                expirationDate
        );
    }

    @AfterEach
    public void tearDown() {
        user = null;
        token = null;
        expirationDate = null;
        passwordToken = null;
    }

    /**
     * Get ID test.
     */
    @Test
    @DisplayName("Get ID")
    public void getId() {
        // when
        Long testId = passwordToken.getId();
        // then
        assertEquals(testId, id);
    }

    /**
     * Get user test.
     */
    @Test
    @DisplayName("Get User")
    public void getUser() {
        // when
        User testUser = passwordToken.getUser();
        // then
        assertEquals(testUser, user);
    }

    /**
     * Get token test.
     */
    @Test
    @DisplayName("Get Token")
    public void getToken() {
        // when
        String testToken = passwordToken.getToken();
        // then
        assertEquals(testToken, token);
    }

    /**
     * Get expiration date test.
     */
    @Test
    @DisplayName("Get Expiration Date")
    public void getExpirationDate() {
        // when
        Date testExpirationDate = passwordToken.getExpirationDate();
        // then
        assertEquals(testExpirationDate, expirationDate);
    }

    /**
     * Set ID test.
     */
    @Test
    @DisplayName("Set ID")
    public void setId() {
        // given
        Long testId = 2L;
        // when
        passwordToken.setId(testId);
        // then
        assertEquals(passwordToken.getId(), testId);
    }

    /**
     * Set user test.
     */
    @Test
    @DisplayName("Set User")
    public void setUser() {
        // given
        User testUser = new User(
                3L,
                "nick",
                "first",
                "last",
                "f.l@gmail.com",
                "1234",
                List.of("user"),
                false
        );
        // when
        passwordToken.setUser(testUser);
        // then
        assertEquals(passwordToken.getUser(), testUser);
    }

    /**
     * Set token test.
     */
    @Test
    @DisplayName("Set Token")
    public void setToken() {
        // given
        String testToken = UUID.randomUUID().toString();
        // when
        passwordToken.setToken(testToken);
        // then
        assertEquals(passwordToken.getToken(), testToken);
    }

    /**
     * Set expiration date test.
     */
    @Test
    @DisplayName("Set Expiration Date")
    public void setExpirationDate() {
        // given
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        Date testExpirationDate = new Date(calendar.getTime().getTime());
        // when
        passwordToken.setExpirationDate(testExpirationDate);
        // then
        assertEquals(passwordToken.getExpirationDate(), testExpirationDate);
    }
}
