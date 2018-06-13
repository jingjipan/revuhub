package com.comp3350.rev_u_hub;
import com.comp3350.rev_u_hub.PersistenceLayer.fakeStorage;
import com.comp3350.rev_u_hub.PersistenceLayer.movie;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

public class fakeStorageTest {
    private com.comp3350.rev_u_hub.PersistenceLayer.movie movie;
    private fakeStorage storage = new fakeStorage();

    @Test
    public void testNotNullStorage(){
        System.out.println("\nStarting test the fake Storage is not empty");
        assertNotNull(storage);
        System.out.println("Finished test the fake Storage is not empty");
    }
    @Test
    public void testStorage() {

        System.out.println("\nStarting test the movie and movie's information is in the Storage");
        String[] pic1 = new String[]{"aa.jpg", "bb.jpg"};

        List<String> reviews = new ArrayList<String>();
        reviews.add("Nice Movie.");
        reviews.add("Poor Movie.");
        reviews.add("Movie is fine.");

        movie = new movie("The Avengers", "A Marvel movie", pic1, reviews);
        assertTrue("The Avengers".equals(storage.searchMovie("The Avengers").getMovieName()));
        assertTrue("A Marvel movie".equals(storage.searchMovie("The Avengers").getIntro()));
        assertTrue(pic1[0].equals(storage.searchMovie("The Avengers").getPicsUrls()[0]));
        assertTrue(pic1[1].equals(storage.searchMovie("The Avengers").getPicsUrls()[1]));
        assertTrue(reviews.equals(storage.searchMovie("The Avengers").getReviews()));
        System.out.println("Finished test the movie movie's information is in the Storage");
    }
    @Test
    public void testMovieNotInStorage() {

        System.out.println("\nStarting test the movie is not in the Storage");
        movie = new movie("Iron Man", null, null, null);
        assertTrue(storage.searchMovie("Iron Man") == null);
        System.out.println("Finished test the movie is not in the Storage");

    }
    }

