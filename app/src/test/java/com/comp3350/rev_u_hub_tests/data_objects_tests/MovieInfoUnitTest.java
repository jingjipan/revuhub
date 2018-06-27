package com.comp3350.rev_u_hub_tests.data_objects_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub_tests.UnitTestHelper;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MovieInfoUnitTest {

    @Test
    public void testMovieEquality(){
        MovieObject[] testMovies = UnitTestHelper.getMovieTestArray();
        for (int i=0; i<UnitTestHelper.TEST_DESCRIPTORS.length; i++) {
            testEquality(testMovies[i],UnitTestHelper.TEST_DESCRIPTORS[i]);
        }
    }

    private void testEquality(MovieObject leftMovie, String description) {
        System.out.println("\nTesting MovieObject's getters and equals() function with " +
                description + ".");

        MovieObject rightMovie =  new MovieObject(leftMovie);

        System.out.println("    Movie title: \"" + leftMovie + "\"");

        assertNotNull(leftMovie);
        assertNotNull(rightMovie);
        assertTrue(leftMovie.equals(rightMovie));
        assertTrue(rightMovie.equals(leftMovie));

        System.out.println("Completed testing MovieObject's getters and equals() function with " +
                description + ".");
    }
}