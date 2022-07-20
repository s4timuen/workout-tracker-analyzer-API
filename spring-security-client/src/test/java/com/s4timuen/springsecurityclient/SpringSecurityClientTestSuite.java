package com.s4timuen.springsecurityclient;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite for all spring security client tests.
 */
@SelectClasses({
        RepositoriesTestSuite.class,
        EntitiesTestSuite.class
})
@Suite
@SuiteDisplayName("Spring Security Client - All Tests")
public class SpringSecurityClientTestSuite {
}
