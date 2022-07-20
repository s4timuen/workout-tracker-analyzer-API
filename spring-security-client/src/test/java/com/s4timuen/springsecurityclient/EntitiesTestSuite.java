package com.s4timuen.springsecurityclient;

import com.s4timuen.springsecurityclient.entity.PasswordChangeTokenTest;
import com.s4timuen.springsecurityclient.entity.PasswordResetTokenTest;
import com.s4timuen.springsecurityclient.entity.UserTest;
import com.s4timuen.springsecurityclient.entity.VerificationTokenTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite for spring security client entities tests.
 */
@SelectClasses({
        UserTest.class,
        VerificationTokenTest.class,
        PasswordResetTokenTest.class,
        PasswordChangeTokenTest.class
})
@Suite
@SuiteDisplayName("Spring Security Client - Entities Test Suite")
public class EntitiesTestSuite { }
