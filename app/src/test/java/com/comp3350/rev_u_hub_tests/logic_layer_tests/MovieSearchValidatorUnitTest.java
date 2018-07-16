package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchValidator;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieSearchValidatorUnitTest {
    private MovieSearch movieSearch = mock(MovieSearch.class);
    private MovieSearchValidator movieSearchValidator = spy(new MovieSearchValidator(movieSearch));

    @Before
    public void setUp() {
        when(movieSearch.getMovieSimple("Interstellar")).thenReturn(
                new MovieObject("Interstellar","","","",0,0)
        );
        when(movieSearch.getMovie("The Dark Night")).thenReturn(
                new MovieObject(
                        "The Dark Night","","","",0,0)
        );
        when(movieSearch.getMovieSimple("Wheel of Fortune")).thenReturn(
                new MovieObject());
        when(movieSearch.getMovie("Jeopardy")).thenReturn(new MovieObject());
    }

    @Test
    public void testSearches() {
        System.out.println("\nTesting MovieSearchValidator exceptions.");
        testFound("Interstellar", true);
        testFound("The Dark Night", false);
        testAbsent("Wheel of Fortune", true);
        testAbsent("Jeopardy", false);
        System.out.println("Completed testing MovieSearchValidator exceptions.");
    }

    private void testFound(String title, boolean simple) {
        boolean thrown = false;

        System.out.println("    Searching should pass for \"" + title + "\"");

        assertNotNull(title);

        try {
            if (simple) movieSearchValidator.getMovieSimple(title).getTitle();
            else movieSearchValidator.getMovie(title).getTitle();
        } catch (MovieDataNotFoundException e) {
            thrown = true;
        }

        assertEquals("Search should not throw an exception", false, thrown);

        try {
            if (simple) verify(movieSearchValidator,
                    times(1)).getMovieSimple(title);
            else verify(movieSearchValidator,
                    times(1)).getMovie(title);
        } catch (MovieDataNotFoundException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }
    }

    private void testAbsent(String title, boolean simple) {
        boolean thrown = false;

        System.out.println("    Searching should fail for \"" + title + "\"");

        assertNotNull(title);

        try {
            if (simple) movieSearchValidator.getMovieSimple(title).getTitle();
            else movieSearchValidator.getMovie(title).getTitle();
        } catch (MovieDataNotFoundException e) {
            thrown = true;
        }

        assertEquals("Search should throw an exception", true, thrown);

        try {
            if (simple) verify(movieSearchValidator,
                    times(1)).getMovieSimple(title);
            else verify(movieSearchValidator,
                    times(1)).getMovie(title);
        } catch (MovieDataNotFoundException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }
    }

    private void printException(Exception e) {
        System.out.println("        Exception: "+e.getMessage());
    }
}