package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.MovieListViewer;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieLists;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub_tests.MovieTestHelper;
import com.comp3350.rev_u_hub_tests.persistence.MoviePersistenceStub;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TEMPORARYMovieListsUnitTest {
    private MoviePersistence moviePersistence = new MoviePersistenceStub();
    private MovieLists movieLists = new MovieListViewer(moviePersistence);

    @Test
    public void testMovieLists() {

        MovieObject[] testMovies = MovieTestHelper.getMovieTestArray();
        List<MovieObject> movieList = new ArrayList<>();

        for (int i = 0; i < MovieTestHelper.TEST_DESCRIPTORS.length; i++) {
            System.out.println("    Storing " +
                    MovieTestHelper.TEST_DESCRIPTORS[i] + ".");
            moviePersistence.addNewMovie(testMovies[i]);
            movieList.add(testMovies[i]);
        }

        Collections.sort(movieList);

        moviePersistence.deleteMovie(moviePersistence.searchMovie("Deadpool").get(0));
        moviePersistence.deleteMovie(moviePersistence.searchMovie("Thor").get(0));
        moviePersistence.deleteMovie(moviePersistence.searchMovie(
                "The Avengers").get(0));

        try {
            testMovieListsEquality(movieList, movieLists.getMovieList(movieList.size()));
        } catch (MovieDataNotFoundException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }
    }
    private void testMovieListsEquality(List<MovieObject> list1, List<MovieObject> list2) {

        System.out.println("\nTesting MovieLists getMovieList() method.");

        assertNotNull(list1);
        assertNotNull(list2);
        assertEquals("Lists must be equal size.",list1.size(),list2.size());

        for(int i=0; i<list1.size(); i++) {
            System.out.println("    Testing item "+i+", rating "+list1.get(i).getRating()+
                    ": "+list1.get(i));

            System.out.println("    Testing item "+i+", rating "+list2.get(i).getRating()+
                    ": "+list2.get(i));
            assertEquals(list1.get(i).getRating(),list2.get(i).getRating(),0.1);
        }

        System.out.println("Completed testing MovieLists getMovieList() method.");
    }

    private void printException(Exception e) {
        System.out.println("        Exception: "+e.getMessage());
    }
}