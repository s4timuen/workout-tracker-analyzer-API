package com.s4timuen.springsecurityclient;

import com.s4timuen.springsecurityclient.repository.PasswordChangeTokenRepositoryTests;
import com.s4timuen.springsecurityclient.repository.PasswordResetTokenRepositoryTests;
import com.s4timuen.springsecurityclient.repository.UserRepositoryTests;
import com.s4timuen.springsecurityclient.repository.VerificationTokenRepositoryTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SelectClasses({
        UserRepositoryTests.class,
        VerificationTokenRepositoryTests.class,
        PasswordResetTokenRepositoryTests.class,
        PasswordChangeTokenRepositoryTests.class
})
@Suite
@SuiteDisplayName("Spring Security Client - Repositories Test Suite")
public class RepositoriesTestSuite { }
