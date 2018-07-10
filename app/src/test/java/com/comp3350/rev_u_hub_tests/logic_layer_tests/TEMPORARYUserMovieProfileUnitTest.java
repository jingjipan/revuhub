package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.RatingManagement;
import com.comp3350.rev_u_hub.logic_layer.ReviewQuery;
import com.comp3350.rev_u_hub.logic_layer.UserMovieStats;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieRatings;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserMovieProfile;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;
import com.comp3350.rev_u_hub_tests.MovieTestHelper;
import com.comp3350.rev_u_hub_tests.ReviewTestHelper;
import com.comp3350.rev_u_hub_tests.UserTestHelper;
import com.comp3350.rev_u_hub_tests.persistence.MoviePersistenceStub;
import com.comp3350.rev_u_hub_tests.persistence.ReviewPersistenceStub;
import com.comp3350.rev_u_hub_tests.persistence.UserAccontPersistenceStub;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TEMPORARYUserMovieProfileUnitTest {
    private MoviePersistence moviePersistence = new MoviePersistenceStub();
    private UserPersistence userPersistence = new UserAccontPersistenceStub();
    private ReviewPersistence reviewPersistence = new ReviewPersistenceStub();
    private MovieSearch movieSearch = new MovieSearchEngine(moviePersistence);
    private UserSearch userSearch = new UserSearchEngine(userPersistence);
    private ReviewSearch reviewSearch = new ReviewQuery(reviewPersistence);
    private MovieRatings movieRatings = new RatingManagement(movieSearch, moviePersistence);
    private UserMovieProfile userMovieProfile = new UserMovieStats(movieSearch,
            userSearch, reviewSearch, movieRatings);

    @Test
    public void testUserMovieProfile() {
        MovieObject movie = MovieTestHelper.randomMovie();
        UserObject user = UserTestHelper.randomUser();
        ReviewObject[] testReviews = ReviewTestHelper.getReviewTestArray(movie.getTitle(),
                user.getUserName());
        List<ReviewObject> reviewList = new ArrayList<>();

        for (int i = 0; i < ReviewTestHelper.TEST_DESCRIPTORS.length; i++) {
            System.out.println("    Storing " +
                    ReviewTestHelper.TEST_DESCRIPTORS[i] + ".");
            reviewPersistence.addNewReview(testReviews[i]);
            reviewList.add(testReviews[i]);
        }

        moviePersistence.addNewMovie(movie);
        userPersistence.addNewUser(user);

        testUserMovieProfileMethods(reviewList, movie, user);
    }
    private void testUserMovieProfileMethods(List<ReviewObject> testReviews, MovieObject movie,
                                      UserObject user) {

        List<MovieObject> movieList = new ArrayList<>();
        double average = 0;

        movieList.add(movie);

        System.out.println("\nTesting UserMovieProfile methods.");

        assertNotNull(testReviews);
        assertNotNull(movie);
        assertNotNull(user);

        System.out.println("    Testing getMovieList().");
        try {
            assertTrue(movieListEquals(movieList,userMovieProfile.getReviewedList(1,
                    user.getUserName())));
        } catch (ReviewDataException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        } catch (UserDataNotFoundException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        } catch (MovieDataNotFoundException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }

        System.out.println("    Testing getLongestReview().");
        Collections.sort(testReviews);
        try {
            assertEquals(testReviews.get(testReviews.size()-1),
                    userMovieProfile.getLongestReview(user.getUserName()));
        } catch (ReviewDataException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        } catch (UserDataNotFoundException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }

        System.out.println("    Testing getReviewRatingAverage().");

        // Making all test data into valid data
        for (int i = 0; i < testReviews.size(); i++) {
            if (testReviews.get(i).isEmpty()) {
                testReviews.set(i,ReviewTestHelper.randomReview(movie.getTitle(),
                        user.getUserName()));
                reviewPersistence.updateReview(testReviews.get(i));
            }

            try {
                average += movieRatings.getAverageRating(testReviews.get(i).getMovieName());
            } catch (MovieDataException e) {
                printException(e);
                assertTrue("Invalid state due to caught exception.",false);
            }
        }

        average /= testReviews.size();

        try {
            System.out.println("        Test data average: "+average);
            System.out.println("        Persistence layer average: "+
                    userMovieProfile.getReviewRatingAverage(user.getUserName()));
            assertEquals(average,userMovieProfile.getReviewRatingAverage(user.getUserName()),
                    0.1);
        } catch (ReviewDataException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        } catch (UserDataNotFoundException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        } catch (MovieDataException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }

        System.out.println("Completed testing UserMovieProfile methods.");
    }

    private boolean movieListEquals(List<MovieObject> list1, List<MovieObject> list2) {
        boolean success = true;

        for(int i=0; i<list2.size(); i++) {
            if (!list2.get(i).isEmpty() && listLacking(list1,list2.get(i))) success = false;
            System.out.println("        Checking inclusion of review ["+i+"] in first list: "+success);
        }

        for(int i=0; i<list1.size(); i++) {
            if (!list1.get(i).isEmpty() && listLacking(list2,list1.get(i))) success = false;
            System.out.println("        Checking inclusion of review ["+i+"] in second list: "+success);
        }

        return success;
    }

    private boolean listLacking(List<MovieObject> list, MovieObject element) {
        boolean found = false;

        for(int i=0; i<list.size(); i++) {
            if (list.get(i).equals(element)) found = true;
        }

        return !found;
    }

    private void printException(Exception e) {
        System.out.println("        Exception: "+e.getMessage());
    }
}