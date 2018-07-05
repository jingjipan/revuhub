package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.CurrentUserStorage;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.ReviewManagement;
import com.comp3350.rev_u_hub.logic_layer.ReviewQuery;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReviewManagerUnitTest {
    private static final String newReviewText = "NEW_REVIEW";
    private ReviewPersistence reviewPersistence = new ReviewPersistenceStub();
    private MoviePersistence moviePersistence = new MoviePersistenceStub();
    private UserPersistence userPersistence = new UserAccontPersistenceStub();
    private ReviewSearch reviewSearch = new ReviewQuery(reviewPersistence);
    private MovieSearch movieSearch = new MovieSearchEngine(moviePersistence);
    private UserSearch userSearch = new UserSearchEngine(userPersistence);
    private UserLogin userLogin = new CurrentUserStorage(userSearch);
    private ReviewManagement reviewManager = new ReviewManagement(reviewPersistence,
            reviewSearch,
            movieSearch,
            userSearch,
            userLogin);

    @Test
    public void testReviewsEditing() {
        MovieObject movie = MovieTestHelper.randomMovie();
        UserObject user = UserTestHelper.randomUser();
        ReviewObject[] testReviews = ReviewTestHelper.getReviewTestArray(movie.getTitle(),
                user.getUserName());

        moviePersistence.addNewMovie(movie);
        userPersistence.addNewUser(user);
        try {
            userLogin.userLogin(user.getUserName(),user.getPassWord());
        } catch (UserDataException e) {
            printException(e);
            assertTrue("Invalid test state due to caught exception.",false);
        }

        for (int i = 0; i < ReviewTestHelper.TEST_DESCRIPTORS.length; i++) {
            testReviewEditing(testReviews[i], ReviewTestHelper.TEST_DESCRIPTORS[i]);
        }
    }
    private void testReviewEditing(ReviewObject testReview, String description) {
        String testUserName = testReview.getUserName();
        String testMovieName = testReview.getMovieName();
        boolean runModifyDelete = true;

        System.out.println("\nTesting ReviewManagement methods' functionality with " +
                description + ".");

        System.out.println("    Review text: \"" + testReview + "\"");

        assertNotNull(testReview);
        assertNotNull(testMovieName);
        assertNotNull(testUserName);

        System.out.println("    Testing review creation...");
        try {
            reviewManager.createReview(testReview.getReview(),testMovieName,testUserName);
        } catch (ReviewCreationException e) {
            printException(e);
            runModifyDelete = false;
        }
        try {
            assertTrue(testReview.equals(reviewSearch.getReview(testMovieName,testUserName)));
        } catch (ReviewDataNotFoundException e) {
            printException(e);
            assertTrue(testReview.isEmpty());
        }

        if (runModifyDelete) {
            System.out.println("    Testing review modification...");
            testReview.setReview(newReviewText);
            try {
                reviewManager.editReview(newReviewText, testMovieName, testUserName);
            } catch (ReviewDataException e) {
                printException(e);
            }
            try {
                assertTrue(testReview.equals(reviewSearch.getReview(testMovieName,testUserName)));
            } catch (ReviewDataNotFoundException e) {
                printException(e);
                assertTrue("Invalid test state due to caught exception.",false);
            }

            System.out.println("    Testing review removal...");
            try {
                reviewManager.removeReview(testReview);
            } catch (ReviewDataException e) {
                printException(e);
            }
            try {
                assertTrue(reviewSearch.getReview(testMovieName,testUserName).isEmpty());
            } catch (ReviewDataNotFoundException e) {
                printException(e);
            }
        }

        System.out.println("Completed testing ReviewManagement methods' functionality with " +
                description + ".");
    }

    private void printException(Exception e) {
        System.out.println("        Exception: "+e.getMessage());
    }
}