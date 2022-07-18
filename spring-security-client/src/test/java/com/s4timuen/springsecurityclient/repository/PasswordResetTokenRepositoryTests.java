package com.s4timuen.springsecurityclient.repository;

import com.s4timuen.springsecurityclient.entity.PasswordResetToken;
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
 * Password reset token repository tests.
 */
@DataJpaTest
@DisplayName("Password Reset Token Repository")
public class PasswordResetTokenRepositoryTests {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
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

    private final PasswordResetToken passwordResetToken = new PasswordResetToken(
            1L,
            user,
            token,
            date
    );

    @BeforeEach
    public void setUp() {
        userRepository.save(user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @AfterEach
    public void tearDown() {
        passwordResetTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * Find password reset token by token test.
     */
    @Test
    @DirtiesContext
    @DisplayName("Find Token By Token")
    public void findByToken() {
        // given
        String invalidToken = UUID.randomUUID().toString();

        // when
        Optional<PasswordResetToken> testToken01 = passwordResetTokenRepository.findByToken(passwordResetToken.getToken());
        Optional<PasswordResetToken> testToken02 = passwordResetTokenRepository.findByToken(invalidToken);

        // then
        assertThat(testToken01).isPresent();
        assertThat(testToken02).isEmpty();
    }
}
