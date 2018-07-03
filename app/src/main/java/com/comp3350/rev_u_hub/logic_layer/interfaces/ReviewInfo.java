package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNoMovieException;

import java.util.List;

public interface ReviewInfo {

    // Search for reviews of a movie
    List<ReviewObject> getReviews(MovieObject movie) throws ReviewDataException;

    // Search for reviews in text form of a movie
    List<String> getReviewsText(MovieObject movie) throws ReviewDataException;

    // Search for reviews by a user
    List<ReviewObject> getReviews(UserObject user) throws ReviewDataException;

    // Search for reviews in text form by a user
    List<String> getReviewsText(UserObject user) throws ReviewDataException;

    // Returns true if a movie has any reviews
    boolean hasReview(MovieObject movie) throws ReviewDataNoMovieException;

    // Returns a movie's average review rating
    double getAverageRating(MovieObject movie) throws ReviewDataNoMovieException;

    // Returns the number of reviews a movie has
    int getReviewCount(MovieObject movie) throws ReviewDataNoMovieException;
}