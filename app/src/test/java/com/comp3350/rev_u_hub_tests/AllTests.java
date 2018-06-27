package com.comp3350.rev_u_hub_tests;

import com.comp3350.rev_u_hub_tests.data_objects_tests.MovieInfoUnitTest;
import com.comp3350.rev_u_hub_tests.logic_layer_tests.SearchMoviesUnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SearchMoviesUnitTest.class,
        MovieInfoUnitTest.class
})
public class AllTests
{

}
