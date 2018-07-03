package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;

import java.util.List;

public interface ReviewInfo {

    //Search for reviews of a movie
    //throws ReviewDataException
    List<ReviewObject> getReviews(MovieObject movie);

    //Search for reviews in text form of a movie
    //throws ReviewDataException
    List<String> getReviewsText(MovieObject movie);

    //Search for reviews by a user
    //throws ReviewDataException
    List<ReviewObject> getReviews(UserObject user);

    //Search for reviews in text form by a user
    //throws ReviewDataException
    List<String> getReviewsText(UserObject user);

    //throws ReviewDataNoMovieException
    boolean hasReview(MovieObject movie);

    //throws ReviewDataNoMovieException
    double getAverageRating(MovieObject movie);

    //throws ReviewDataNoMovieException
    int getReviewCount(MovieObject movie);
}