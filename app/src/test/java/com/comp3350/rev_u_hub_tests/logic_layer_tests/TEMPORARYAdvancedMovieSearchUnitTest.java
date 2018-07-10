package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub_tests.MovieTestHelper;
import com.comp3350.rev_u_hub_tests.persistence.MoviePersistenceStub;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class TEMPORARYAdvancedMovieSearchUnitTest {
    private MoviePersistence persistenceLayer = new MoviePersistenceStub();
    private static final String allChars = "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    private MovieSearch movieSearch = new MovieSearchEngine(persistenceLayer);

    @Test
    public void testSearches(){
        System.out.println("\nTesting MovieInMemorySearchEngine getMovie functionality.");
        testSearch("Thor","t H o r");
        testSearch("The Avengers","aveners");
        testSearch("Deadpool","d'edPOO         l");
        System.out.println("Completed testing MovieInMemorySearchEngine getMovie functionality.");
    }

    private void testSearch(String expectedTitle, String searchTitle) {
        System.out.println("    Searching for \"" + expectedTitle + "\" using \""
                + searchTitle + "\".");

        assertNotNull(expectedTitle);
        assertNotNull(searchTitle);

        assertEquals(expectedTitle,movieSearch.getMovie(searchTitle).getTitle());
    }
}