package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.ReviewQuery;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewSearch;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;
import com.comp3350.rev_u_hub_tests.MovieTestHelper;
import com.comp3350.rev_u_hub_tests.ReviewTestHelper;
import com.comp3350.rev_u_hub_tests.UserTestHelper;
import com.comp3350.rev_u_hub_tests.persistence.ReviewPersistenceStub;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReviewSearchUnitTest {
    private ReviewPersistence myPersistenceLayer = new ReviewPersistenceStub();
    private ReviewSearch reviewSearch = new ReviewQuery(myPersistenceLayer);

    @Test
    public void testReviewSearch() {
        MovieObject movie = MovieTestHelper.randomMovie();
        UserObject user = UserTestHelper.randomUser();
        ReviewObject[] testReviews = ReviewTestHelper.getReviewTestArray(movie.getTitle(),
                user.getUserName());
        List<ReviewObject> reviewList = new ArrayList<>();

        for (int i = 0; i < ReviewTestHelper.TEST_DESCRIPTORS.length; i++) {
            System.out.println("    Storing " +
                    ReviewTestHelper.TEST_DESCRIPTORS[i] + ".");
            myPersistenceLayer.addNewReview(testReviews[i]);
            reviewList.add(testReviews[i]);
        }

        testReviewSearchList(reviewList, movie, user);
    }
    private void testReviewSearchList(List<ReviewObject> testReviews, MovieObject movie,
                                      UserObject user) {

        System.out.println("\nTesting ReviewQuery list methods.");

        assertNotNull(testReviews);
        assertNotNull(movie);
        assertNotNull(user);

        System.out.println("    Testing review list via movie title.");
        try {
            assertTrue(reviewListEquals(testReviews,reviewSearch.getReviews(movie)));
        } catch (ReviewDataException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }

        System.out.println("    Testing review list via username.");
        try {
            assertTrue(reviewListEquals(testReviews,reviewSearch.getReviews(user)));
        } catch (ReviewDataException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }

        System.out.println("    Testing string list via movie title.");
        try {
            assertTrue(stringListEquals(toStringList(testReviews),
                    reviewSearch.getReviewsText(movie)));
        } catch (ReviewDataException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }

        System.out.println("    Testing string list via username.");
        try {
            assertTrue(stringListEquals(toStringList(testReviews),
                    reviewSearch.getReviewsText(user)));
        } catch (ReviewDataException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }

        System.out.println("Completed testing ReviewQuery list methods.");
    }

    private boolean stringListEquals(List<String> list1, List<String> list2) {
        boolean success = true;

        for(int i=0; i<list2.size(); i++) {
            if (list2.get(i)!=null && !list2.get(i).equals("") && listLacking(list1,list2.get(i))) success = false;
            System.out.println("        Checking inclusion of string ["+i+"] in first list: "+success);
        }

        for(int i=0; i<list1.size(); i++) {
            if (list1.get(i)!=null && !list1.get(i).equals("") &&  listLacking(list2,list1.get(i))) success = false;
            System.out.println("        Checking inclusion of string ["+i+"] in second list: "+success);
        }

        return success;
    }

    private boolean reviewListEquals(List<ReviewObject> list1, List<ReviewObject> list2) {
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

    private boolean listLacking(List<ReviewObject> list, ReviewObject element) {
        boolean found = false;

        for(int i=0; i<list.size(); i++) {
            if (list.get(i).equals(element)) found = true;
        }

        return !found;
    }

    private boolean listLacking(List<String> list, String element) {
        boolean found = false;

        for(int i=0; i<list.size(); i++) {
            if (list.get(i).equals(element)) found = true;
        }

        return !found;
    }

    private List<String> toStringList(List<ReviewObject> list) {
        List<String> stringList = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            stringList.add(list.get(i).getReview());
        }
        return stringList;
    }

    private void printException(Exception e) {
        System.out.println("        Exception: "+e.getMessage());
    }
}