package com.s4timuen.springsecurityclient.repository;

import com.s4timuen.springsecurityclient.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * User repository tests.
 */
@DataJpaTest
public class UserRepositoryTests {

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

    @BeforeEach
    public void setUp() {
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    /**
     * Find user by nickname test.
     */
    @Test
    @DirtiesContext
    @DisplayName("User Repository - Find User By Name Test")
    public void findByNickname() {
        // when
        Optional<User> testUser01 = userRepository.findByNickname(user.getNickname());
        Optional<User> testUser02 = userRepository.findByNickname("kevin");

        // then
        assertThat(testUser01).isPresent();
        assertThat(testUser02).isEmpty();
    }

    /**
     * Find user by Email test.
     */
    @Test
    @DirtiesContext
    @DisplayName("User Repository - Find User By Email Test")
    public void findByEmail() {
        // when
        Optional<User> testUser01 = userRepository.findByEmail(user.getEmail());
        Optional<User> testUser02 = userRepository.findByNickname("kevin@gmail.com");

        // then
        assertThat(testUser01).isPresent();
        assertThat(testUser02).isEmpty();
    }
}
