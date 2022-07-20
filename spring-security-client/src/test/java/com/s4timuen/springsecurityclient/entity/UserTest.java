package com.s4timuen.springsecurityclient.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for user entity.
 */
@SpringBootTest
@DisplayName("User")
public class UserTest {

    private final Long id = 1L;
    private final String nickname = "nick";
    private final String firstName = "first";
    private final String lastName = "last";
    private final String email = "first.last@gmail.com";
    private final String password = "123";
    private final List<String> roles = List.of("user");
    private final boolean enabled = false;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(
                id,
                nickname,
                firstName,
                lastName,
                email,
                password,
                roles,
                enabled
        );
    }

    @AfterEach
    public void tearDown() {
        user = null;
    }

    /**
     * Get ID test.
     */
    @Test
    @DisplayName("Get ID")
    public void getId() {
        // when
        Long testId = user.getId();
        // then
        assertEquals(testId, id);
    }

    /**
     * Get nickname test.
     */
    @Test
    @DisplayName("Get Nickname")
    public void getNickname() {
        // when
        String testNickname = user.getNickname();
        // then
        assertEquals(testNickname, nickname);
    }

    /**
     * Get first name test.
     */
    @Test
    @DisplayName("Get First Name")
    public void getFirstName() {
        // when
        String testFirstName = user.getFirstName();
        // then
        assertEquals(testFirstName, firstName);
    }

    /**
     * Get last name test.
     */
    @Test
    @DisplayName("Get Last Name")
    public void getLastName() {
        // when
        String testLastName = user.getLastName();
        // then
        assertEquals(testLastName, lastName);
    }

    /**
     * Get email test.
     */
    @Test
    @DisplayName("Get Email")
    public void getEmail() {
        // when
        String testEmail = user.getEmail();
        // then
        assertEquals(testEmail, email);
    }

    /**
     * Get password test.
     */
    @Test
    @DisplayName("Get Password")
    public void getPassword() {
        // when
        String testPassword = user.getPassword();
        // then
        assertEquals(testPassword, password);
    }

    /**
     * Get roles test.
     */
    @Test
    @DisplayName("Get Roles")
    public void getRoles() {
        // when
        List<String> testRoles = user.getRoles();
        // then
        assertEquals(testRoles, roles);
    }

    /**
     * Is enabled test.
     */
    @Test
    @DisplayName("Is Enabled")
    public void isEnabled() {
        // when
        boolean testEnabled = user.isEnabled();
        // then
        assertEquals(testEnabled, enabled);
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
        user.setId(testId);
        // then
        assertEquals(testId, user.getId());

    }

    /**
     * Set nickname test.
     */
    @Test
    @DisplayName("Set Nickname")
    public void setNickname() {
        // given
        String testNickname = "test";
        // when
        user.setNickname(testNickname);
        // then
        assertEquals(testNickname, user.getNickname());
    }

    /**
     * Set first name test.
     */
    @Test
    @DisplayName("Set First Name")
    public void setFirstName() {
        // given
        String testFirstName = "test";
        // when
        user.setFirstName(testFirstName);
        // then
        assertEquals(testFirstName, user.getFirstName());
    }

    /**
     * Set last name test.
     */
    @Test
    @DisplayName("Set Last Name")
    public void setLastName() {
        // given
        String testLastName = "test";
        // when
        user.setLastName(testLastName);
        // then
        assertEquals(testLastName, user.getLastName());
    }

    /**
     * Set email test.
     */
    @Test
    @DisplayName("Set Email")
    public void setEmail() {
        // given
        String testEmail = "last.first@gmail.com";
        // when
        user.setEmail(testEmail);
        // then
        assertEquals(testEmail, user.getEmail());
    }

    /**
     * Set password test
     */
    @Test
    @DisplayName("Set Password")
    public void setPassword() {
        // given
        String testPassword = "321";
        // when
        user.setPassword(testPassword);
        // then
        assertEquals(testPassword, user.getPassword());
    }

    /**
     * Set roles test.
     */
    @Test
    @DisplayName("Set Roles")
    public void setRoles() {
        // given
        List<String> testRoles = List.of("admin");
        // when
        user.setRoles(testRoles);
        // then
        assertEquals(testRoles, user.getRoles());
    }

    /**
     * Set enabled test.
     */
    @Test
    @DisplayName("Set Enabled")
    public void setEnabled() {
        // given
        boolean testEnabled = true;
        // when
        user.setEnabled(testEnabled);
        // then
        assertEquals(testEnabled, user.isEnabled());
    }

    /**
     * Builder test.
     */
    @Test
    @DisplayName("Builder")
    public void builder() {
        // when
        User testUser = User.builder()
                .id(id)
                .nickname(nickname)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .roles(roles)
                .enabled(enabled)
                .build();
        // then
        assertNotNull(testUser);
        assertEquals(testUser.getId(), id);
        assertEquals(testUser.getNickname(), nickname);
        assertEquals(testUser.getFirstName(), firstName);
        assertEquals(testUser.getLastName(), lastName);
        assertEquals(testUser.getEmail(), email);
        assertEquals(testUser.getPassword(), password);
        assertEquals(testUser.getRoles(), roles);
        assertEquals(testUser.isEnabled(), enabled);
    }
}
