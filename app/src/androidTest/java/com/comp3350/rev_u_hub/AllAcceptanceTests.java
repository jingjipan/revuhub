package com.comp3350.rev_u_hub;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginTest.class,
        CreateUserTest.class,
        SearchMovieTest.class,
        UserProfileTest.class,
        ChangePasswordTest.class,
        AddReviewTest.class,})
public class AllAcceptanceTests {
}

