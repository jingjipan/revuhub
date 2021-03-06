package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.RatingManagement;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieRatings;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub_tests.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class MovieRatingAccessIT {
    private static final int newRating = 5;
    private MoviePersistence myPersistenceLayer;
    private File tempDB;
    private MovieSearch movieSearch;
    private MovieRatings movieRatings;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.myPersistenceLayer = Services.getMoviePersistence();
        movieSearch = new MovieSearchEngine(myPersistenceLayer);
        movieRatings = new RatingManagement(movieSearch,myPersistenceLayer);
    }

    @Test
    public void testMovieRatingAccess() {
        System.out.println("Start testing access the rating of a movie.");
        double rating=0;
        int count=0;
        try{
            movieRatings.hasRating("Thor");
        }catch(MovieDataNotFoundException e){
            e.printStackTrace();
        }

        try{
            rating = movieRatings.getAverageRating("Thor");
            count= movieRatings.getRatingCount("Thor");
        }catch(MovieDataException e){
            e.printStackTrace();
        }

        assertTrue("movie rating should be 5.0",rating==5.0);
        assertTrue("rating count should be 1",count==1);

        System.out.println("Finished testing access the rating of a movie.");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }
}
