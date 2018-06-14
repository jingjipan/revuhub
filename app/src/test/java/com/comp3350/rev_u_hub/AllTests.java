package com.comp3350.rev_u_hub;

import com.comp3350.rev_u_hub.DMObjects.MovieInfoUnitTest;
import com.comp3350.rev_u_hub.LogicLayer.SearchMoviesUnitTest;
import com.comp3350.rev_u_hub.PersistenceLayer.fakeStorageTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SearchMoviesUnitTest.class,
        fakeStorageTest.class,
        MovieInfoUnitTest.class
})
public class AllTests
{

}
