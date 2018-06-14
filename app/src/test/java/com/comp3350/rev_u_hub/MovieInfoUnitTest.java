package com.comp3350.rev_u_hub;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.LogicLayer.UnitTestHelper;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MovieInfoUnitTest {

    @Test
    public void testMovieEquality(){
        MovieDMObject[] testMovies = UnitTestHelper.getMovieTestArray();
        for (int i=0; i<UnitTestHelper.TEST_DESCRIPTORS.length; i++) {
            testEquality(testMovies[i],UnitTestHelper.TEST_DESCRIPTORS[i]);
        }
    }

    private void testEquality(MovieDMObject leftMovie, String description) {
        System.out.println("\nTesting MovieDMObject's getters and equals() function with " +
                description + ".");

        MovieDMObject rightMovie =  new MovieDMObject(leftMovie);

        assertNotNull(leftMovie);
        assertNotNull(rightMovie);
        assertTrue(leftMovie.equals(rightMovie));
        assertTrue(rightMovie.equals(leftMovie));

        System.out.println("Completed testing MovieDMObject's getters and equals() function with " +
                description + ".");
    }
}