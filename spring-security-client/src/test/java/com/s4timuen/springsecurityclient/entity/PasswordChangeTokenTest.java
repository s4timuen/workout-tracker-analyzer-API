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
 * Password change token tests.
 */
@SpringBootTest
@DisplayName("Password Change Token")
public class PasswordChangeTokenTest {

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
    PasswordChangeToken passwordChangeToken;

    @BeforeEach
    public void setUp() {
        token = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        expirationDate = new Date(calendar.getTime().getTime());
        passwordChangeToken = new PasswordChangeToken(
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
        passwordChangeToken = null;
    }

    /**
     * Get ID test.
     */
    @Test
    @DisplayName("Get ID")
    public void getId() {
        // when
        Long testId = passwordChangeToken.getId();
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
        User testUser = passwordChangeToken.getUser();
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
        String testToken = passwordChangeToken.getToken();
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
        Date testExpirationDate = passwordChangeToken.getExpirationDate();
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
        passwordChangeToken.setId(testId);
        // then
        assertEquals(passwordChangeToken.getId(), testId);
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
        passwordChangeToken.setUser(testUser);
        // then
        assertEquals(passwordChangeToken.getUser(), testUser);
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
        passwordChangeToken.setToken(testToken);
        // then
        assertEquals(passwordChangeToken.getToken(), testToken);
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
        passwordChangeToken.setExpirationDate(testExpirationDate);
        // then
        assertEquals(passwordChangeToken.getExpirationDate(), testExpirationDate);
    }
}
