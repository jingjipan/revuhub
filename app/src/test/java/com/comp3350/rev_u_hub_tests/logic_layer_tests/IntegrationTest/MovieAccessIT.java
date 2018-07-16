package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.MovieListViewer;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub_tests.utils.TestUtils;
import com.comp3350.rev_u_hub.Application.Services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class MovieAccessIT {
    private MovieListViewer movieListViewer;
    private File tempDB;
    private MoviePersistence myPersistenceLayer;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.myPersistenceLayer = Services.getMoviePersistence();
        this.movieListViewer = new MovieListViewer(myPersistenceLayer);
    }

    @Test
    public void testAccessMovieLists() {
        System.out.println("Start testing access a list of movies.");
        List<MovieObject> movieLists = new ArrayList<MovieObject>();

        try{
            movieLists = movieListViewer.getMovieList(3);
        }catch(MovieDataNotFoundException e){
            e.printStackTrace();
        }
        assertNotNull("movie list should not be null",movieLists);
        assertTrue("Thor".equals(movieLists.get(0).getTitle()));

        System.out.println("Finished testing access a list of movies.");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }



}
