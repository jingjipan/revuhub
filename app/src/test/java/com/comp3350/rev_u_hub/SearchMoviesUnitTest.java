package com.comp3350.rev_u_hub;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.LogicLayer.InitializeBackend;
import com.comp3350.rev_u_hub.LogicLayer.LogicInterface;
import com.comp3350.rev_u_hub.LogicLayer.UnitTestHelper;
import com.comp3350.rev_u_hub.LogicLayer.LogicConstants;
import com.comp3350.rev_u_hub.PersistenceLayer.PersistenceInterface;
import com.comp3350.rev_u_hub.PersistenceLayer.fakeStorage;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SearchMoviesUnitTest {
    private PersistenceInterface persistenceLayer = new fakeStorage();
    private LogicInterface logicLayer = InitializeBackend.createLogicLayer(persistenceLayer);

    @Test
    public void testSearches(){
        MovieDMObject[] testMovies = UnitTestHelper.getMovieTestArray();
        for (int i=0; i<UnitTestHelper.TEST_DESCRIPTORS.length; i++) {
            testSearch(testMovies[i],UnitTestHelper.TEST_DESCRIPTORS[i]);
        }
    }

    private void testSearch(MovieDMObject testMovie, String description) {
        String testTitle = testMovie.getTitle();

        System.out.println("\nTesting SearchHandler getMovie functionality with " +
                description + ".");

        persistenceLayer.addNewMovie(testTitle, (new MovieDMObject(testMovie)).getMovie());

        assertNotNull(testMovie);
        assertNotNull(testTitle);

        assertTrue(testMovie.equals(logicLayer.getMovieSimple(testTitle)));

        assertTrue(testMovie.equals(logicLayer.getMovie(
                UnitTestHelper.randomDeletion(testTitle))));
        assertTrue(testMovie.equals(logicLayer.getMovie(
                UnitTestHelper.randomTransposition(testTitle))));
        assertTrue(testMovie.equals(logicLayer.getMovie(
                UnitTestHelper.randomInsertion(testTitle, LogicConstants.allChars))));
        assertTrue(testMovie.equals(logicLayer.getMovie(
                UnitTestHelper.randomSubstitution(testTitle, LogicConstants.allChars))));

        System.out.println("Completed testing SearchHandler getMovie functionality with " +
                description + ".");
    }
}