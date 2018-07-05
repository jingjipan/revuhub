package com.comp3350.rev_u_hub_tests;

import com.comp3350.rev_u_hub_tests.data_objects_tests.MovieInfoUnitTest;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.AccountManagerUnitTest;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.MovieInMemorySearchUnitTest;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.MovieRatingsUnitTest;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.ReviewManagerUnitTest;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.ReviewSearchUnitTest;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.UserInMemorySearchUnitTest;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.UserLoginUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MovieInMemorySearchUnitTest.class,
        UserInMemorySearchUnitTest.class,
        ReviewSearchUnitTest.class,
        AccountManagerUnitTest.class,
        MovieRatingsUnitTest.class,
        ReviewManagerUnitTest.class,
        UserLoginUnitTest.class,
        MovieInfoUnitTest.class
})
public class AllTests
{

}
