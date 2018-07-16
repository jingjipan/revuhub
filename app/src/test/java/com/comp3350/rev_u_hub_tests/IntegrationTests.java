package com.comp3350.rev_u_hub_tests;


import com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest.AccountAccessIT;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest.AccountCreateIT;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest.AccountModifyIT;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest.AccountRemoveIT;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest.MovieAccessIT;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest.MovieRatingAccessIT;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest.MovieRatingAdditionIT;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest.ReviewAccessIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountAccessIT.class,
        AccountCreateIT.class,
        AccountModifyIT.class,
        AccountRemoveIT.class,
        MovieAccessIT.class,
        MovieRatingAccessIT.class,
        MovieRatingAdditionIT.class,
        ReviewAccessIT.class
})
public class IntegrationTests {
}
