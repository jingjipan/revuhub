package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.RatingManagement;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataInvalidRatingException;
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

public class MovieRatingAdditionIT {
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
    public void testMovieRatingAddtion() {
        System.out.println("Start testing Movie Rating addition");
        try{
            movieRatings.hasRating("Thor");
        }catch(MovieDataNotFoundException e){
            e.printStackTrace();
        }

        try{
            movieRatings.addRating("Thor",1);
        }catch(MovieDataException e){
            e.printStackTrace();
        }

        double rating=0;
        int count=0;
        try{
            rating = movieRatings.getAverageRating("Thor");
            count= movieRatings.getRatingCount("Thor");
        }catch(MovieDataException e){
            e.printStackTrace();
        }

        assertTrue("movie rating should be 3.0",rating==3.0);
        assertTrue("rating count should be 2",count==2);

        System.out.println("Finished test Movie Rating addition");
    }
    @Test
    public void testMovieRatingInvalidAddtion() {
        System.out.println("Start testing Movie Rating Invalid addition");
        boolean cond = false;
        try{
            movieRatings.hasRating("Thor");
        }catch(MovieDataNotFoundException e){
            e.printStackTrace();
        }

        try{
            movieRatings.addRating("Thor",6);
        }catch (MovieDataInvalidRatingException e){
            cond = true;
        }catch(MovieDataException e){
            e.printStackTrace();
        }
        assertTrue("Reviews must be an integer from 1 to 5", cond);

        System.out.println("Finished test Movie Rating Invalid addition");
    }


    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }
}
