package com.s4timuen.springsecurityclient.repository;

import com.s4timuen.springsecurityclient.entity.User;
import com.s4timuen.springsecurityclient.entity.VerificationToken;
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
 * Verification token repository tests.
 */
@DataJpaTest
@DisplayName("Verification Token Repository")
public class VerificationTokenRepositoryTests {

    @Autowired
    @SuppressWarnings("unused")
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    @SuppressWarnings("unused")
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

    private final VerificationToken verificationToken = new VerificationToken(
            1L,
            user,
            token,
            date
    );

    @BeforeEach
    public void setUp() {
        userRepository.save(user);
        verificationTokenRepository.save(verificationToken);
    }

    @AfterEach
    public void tearDown() {
        verificationTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    /**
     * Find verification token by token test.
     */
    @Test
    @DirtiesContext
    @DisplayName("Find Token By Token")
    public void findByToken() {
        // given
        String invalidToken = UUID.randomUUID().toString();
        // when
        Optional<VerificationToken> testToken01 = verificationTokenRepository.findByToken(verificationToken.getToken());
        Optional<VerificationToken> testToken02 = verificationTokenRepository.findByToken(invalidToken);
        // then
        assertThat(testToken01).isPresent();
        assertThat(testToken02).isEmpty();
    }
}
