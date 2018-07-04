package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub_tests.MovieTestHelper;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub_tests.persistence.MoviePersistenceStub;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchMoviesUnitTest {
    private MoviePersistence persistenceLayer = new MoviePersistenceStub();
    private static final String allChars = "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    private MovieSearch movieSearch = new MovieSearchEngine(persistenceLayer);

    @Test
    public void testSearches(){
        MovieObject[] testMovies = MovieTestHelper.getMovieTestArray();
        for (int i = 0; i< MovieTestHelper.TEST_DESCRIPTORS.length; i++) {
            testSearch(testMovies[i], MovieTestHelper.TEST_DESCRIPTORS[i]);
        }
    }

    private void testSearch(MovieObject testMovie, String description) {
        String testTitle = testMovie.getTitle();

        System.out.println("\nTesting MovieSearchEngine getMovie functionality with " +
                description + ".");

        persistenceLayer.addNewMovie(new MovieObject(testMovie).getMovie());

        System.out.println("    Movie title: \"" + testTitle + "\"");

        assertNotNull(testMovie);
        assertNotNull(testTitle);

        assertTrue(testMovie.equals(movieSearch.getMovieSimple(testTitle)));

        assertTrue(testMovie.equals(movieSearch.getMovie(
                MovieTestHelper.randomDeletion(testTitle))));
        assertTrue(testMovie.equals(movieSearch.getMovie(
                MovieTestHelper.randomTransposition(testTitle))));
        assertTrue(testMovie.equals(movieSearch.getMovie(
                MovieTestHelper.randomInsertion(testTitle, allChars))));
        assertTrue(testMovie.equals(movieSearch.getMovie(
                MovieTestHelper.randomSubstitution(testTitle, allChars))));

        System.out.println("Completed testing MovieSearchEngine getMovie functionality with " +
                description + ".");
    }
}