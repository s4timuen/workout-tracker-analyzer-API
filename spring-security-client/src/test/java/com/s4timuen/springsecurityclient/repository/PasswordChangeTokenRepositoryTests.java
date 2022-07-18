package com.s4timuen.springsecurityclient.repository;

import com.s4timuen.springsecurityclient.entity.PasswordChangeToken;
import com.s4timuen.springsecurityclient.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Password change token repository tests.
 */
@DataJpaTest
@DisplayName("Password Change Token Repository")
public class PasswordChangeTokenRepositoryTests {

    @Autowired
    private PasswordChangeTokenRepository passwordChangeTokenRepository;
    @Autowired
    private UserRepository userRepository;

    private final User user = new User(
            1L,
            "nick",
            "first",
            "last",
            "first.last@gmail.com",
            "123",
            List.of("user"),
            false
    );
    private final String token = UUID.randomUUID().toString();
    private final Date date = Calendar.getInstance().getTime();

    private final PasswordChangeToken passwordChangeToken = new PasswordChangeToken(
            1L,
            user,
            token,
            date
    );

    @BeforeEach
    public void setUp() {
        userRepository.save(user);
        passwordChangeTokenRepository.save(passwordChangeToken);
    }

    @AfterEach
    public void tearDown() {
        passwordChangeTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * Find password change token by token test.
     */
    @Test
    @DirtiesContext
    @DisplayName("Find Token By Token")
    public void findByToken() {
        // given
        String invalidToken = UUID.randomUUID().toString();

        // when
        Optional<PasswordChangeToken> testToken01 = passwordChangeTokenRepository.findByToken(passwordChangeToken.getToken());
        Optional<PasswordChangeToken> testToken02 = passwordChangeTokenRepository.findByToken(invalidToken);

        // then
        assertThat(testToken01).isPresent();
        assertThat(testToken02).isEmpty();
    }
}
