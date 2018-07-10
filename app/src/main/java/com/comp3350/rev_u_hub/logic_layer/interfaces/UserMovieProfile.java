package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;

import java.util.List;

public interface UserMovieProfile {

    // Return a list of up to n of the movies a user has reviewed
    List<MovieObject> getReviewedList(int n, String username)
            throws ReviewDataException, UserDataNotFoundException,
            MovieDataNotFoundException;

    // Return a user's longest review
    ReviewObject getLongestReview(String username)
            throws ReviewDataException, UserDataNotFoundException;

    // Return the average rating of the movies a user has reviewed
    double getReviewRatingAverage(String username)
            throws ReviewDataException, UserDataNotFoundException,
            MovieDataException;
}
