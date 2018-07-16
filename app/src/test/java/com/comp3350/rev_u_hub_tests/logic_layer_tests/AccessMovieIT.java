package com.comp3350.rev_u_hub_tests.logic_layer_tests;

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


public class AccessMovieIT {
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
    public void testMovieLists() {
        List<MovieObject> movieLists = new ArrayList<MovieObject>();
        //movieLists = movieListViewer.getMovieList(3);

        try{
            movieLists = movieListViewer.getMovieList(3);
        }catch(MovieDataNotFoundException e){
            e.printStackTrace();
        }
        assertNotNull("movie list should not be null",movieLists);
        assertTrue("Thor".equals(movieLists.get(0).getTitle()));

        System.out.println("Finished test AccessMovie");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }

}
