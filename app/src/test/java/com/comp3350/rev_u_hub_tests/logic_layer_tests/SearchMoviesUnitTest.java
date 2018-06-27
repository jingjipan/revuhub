package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.MovieAccess;
import com.comp3350.rev_u_hub_tests.UnitTestHelper;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub.persistence_layer.stubs.MoviePersistenceStub;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchMoviesUnitTest {
    private MoviePersistence persistenceLayer = new MoviePersistenceStub();
    private static final String allChars = "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    private MovieAccess movieSearch = Services.getMovieAccess(persistenceLayer);

    @Test
    public void testSearches(){
        MovieObject[] testMovies = UnitTestHelper.getMovieTestArray();
        for (int i=0; i<UnitTestHelper.TEST_DESCRIPTORS.length; i++) {
            testSearch(testMovies[i],UnitTestHelper.TEST_DESCRIPTORS[i]);
        }
    }

    private void testSearch(MovieObject testMovie, String description) {
        String testTitle = testMovie.getTitle();

        System.out.println("\nTesting MovieSearchEngine getMovie functionality with " +
                description + ".");

        persistenceLayer.addNewMovie(testTitle, (new MovieObject(testMovie)).getMovie());

        assertNotNull(testMovie);
        assertNotNull(testTitle);

        assertTrue(testMovie.equals(movieSearch.getMovieSimple(testTitle)));

        assertTrue(testMovie.equals(movieSearch.getMovie(
                UnitTestHelper.randomDeletion(testTitle))));
        assertTrue(testMovie.equals(movieSearch.getMovie(
                UnitTestHelper.randomTransposition(testTitle))));
        assertTrue(testMovie.equals(movieSearch.getMovie(
                UnitTestHelper.randomInsertion(testTitle, allChars))));
        assertTrue(testMovie.equals(movieSearch.getMovie(
                UnitTestHelper.randomSubstitution(testTitle, allChars))));

        System.out.println("Completed testing MovieSearchEngine getMovie functionality with " +
                description + ".");
    }
}