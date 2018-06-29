package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;

public interface ReviewManager {

    //throws ReviewCreationException
    ReviewObject createReview(String setReview, String movieName, String userName, int rating);

    //throws ReviewDataException
    ReviewObject editReview(String reviewName, String movieName, String userName, int rating);

    //throws ReviewDataException
    void removeReview(String reviewName);

    //throws ReviewDataNoMovieException
    boolean hasReview(MovieObject movie);

    //throws ReviewDataNoMovieException
    int getAverageRating(MovieObject movie);

    //throws ReviewDataNoMovieException
    int getReviewCount(MovieObject movie);
}