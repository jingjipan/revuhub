package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNotFoundException;

import java.util.List;

public interface ReviewSearch {

    // Search for reviews of a movie
    List<ReviewObject> getReviews(MovieObject movie) throws ReviewDataException;

    // Search for reviews in text form of a movie
    List<String> getReviewsText(MovieObject movie) throws ReviewDataException;

    // Search for reviews by a user
    List<ReviewObject> getReviews(UserObject user) throws ReviewDataException;

    // Search for reviews in text form by a user
    List<String> getReviewsText(UserObject user) throws ReviewDataException;

    // Search for a review of a movie by a user
    ReviewObject getReview(MovieObject movie, UserObject user) throws ReviewDataNotFoundException;
    ReviewObject getReview(String movieName, String userName)
            throws ReviewDataNotFoundException;

    // Returns true if a review of a movie by a user exists
    boolean reviewExists(String movieName, String userName);
}