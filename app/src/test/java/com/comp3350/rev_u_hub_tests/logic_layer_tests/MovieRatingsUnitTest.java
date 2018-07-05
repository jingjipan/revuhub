package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.MovieInMemorySearch;
import com.comp3350.rev_u_hub.logic_layer.RatingManagement;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieRatings;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub_tests.MovieTestHelper;
import com.comp3350.rev_u_hub_tests.persistence.MoviePersistenceStub;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MovieRatingsUnitTest {
    private static final int newRating = 5;
    private MoviePersistence persistenceLayer = new MoviePersistenceStub();
    private MovieSearch movieSearch = new MovieInMemorySearch(persistenceLayer);
    private MovieRatings movieRatings = new RatingManagement(movieSearch,persistenceLayer);

    @Test
    public void testMovieRatings() {
        MovieObject[] testMovies = MovieTestHelper.getMovieTestArray();
        for (int i = 0; i < MovieTestHelper.TEST_DESCRIPTORS.length; i++) {
            testMovieRating(testMovies[i], MovieTestHelper.TEST_DESCRIPTORS[i]);
        }
    }
    private void testMovieRating(MovieObject testMovie, String description) {
        String testTitle = testMovie.getTitle();

        System.out.println("\nTesting RatingManagement methods' functionality with " +
                description + ".");

        persistenceLayer.addNewMovie(new MovieObject(testMovie).getMovie());

        System.out.println("    Movie Title: \"" + testMovie + "\"");

        assertNotNull(testMovie);
        assertNotNull(testTitle);

        try {
            System.out.println("    Testing rating info retrieval...");
            assertTrue(movieRatings.hasRating(testTitle));
            assertTrue(movieRatings.getRatingCount(testTitle)==testMovie.getCount());
            assertTrue(movieRatings.getAverageRating(testTitle)==testMovie.getRating());
            System.out.println("    Testing movie rating addition...");
            testMovie.updateRating(newRating);
            movieRatings.addRating(testTitle,newRating);
            assertTrue(movieRatings.getRatingCount(testTitle)==testMovie.getCount());
            assertTrue(movieRatings.getAverageRating(testTitle)==testMovie.getRating());
        } catch (MovieDataException e) {
            printException(e);
        }

        System.out.println("Completed testing RatingManagement methods' functionality with " +
                description + ".");
    }

    private void printException(Exception e) {
        System.out.println("        Exception: "+e.getMessage());
    }
}