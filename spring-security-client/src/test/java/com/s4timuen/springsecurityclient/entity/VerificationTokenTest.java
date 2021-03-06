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
 * tests for verification token entity.
 */
@SpringBootTest
@DisplayName("Verification Token")
public class VerificationTokenTest {

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
    VerificationToken verificationToken;

    @BeforeEach
    public void setUp() {
        token = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        expirationDate = new Date(calendar.getTime().getTime());
        verificationToken = new VerificationToken(
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
        verificationToken = null;
    }

    /**
     * Get ID test.
     */
    @Test
    @DisplayName("Get ID")
    public void getId() {
        // when
        Long testId = verificationToken.getId();
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
        User testUser = verificationToken.getUser();
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
        String testToken = verificationToken.getToken();
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
        Date testExpirationDate = verificationToken.getExpirationDate();
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
        verificationToken.setId(testId);
        // then
        assertEquals(verificationToken.getId(), testId);
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
        verificationToken.setUser(testUser);
        // then
        assertEquals(verificationToken.getUser(), testUser);
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
        verificationToken.setToken(testToken);
        // then
        assertEquals(verificationToken.getToken(), testToken);
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
        verificationToken.setExpirationDate(testExpirationDate);
        // then
        assertEquals(verificationToken.getExpirationDate(), testExpirationDate);
    }
}
