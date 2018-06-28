package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import java.util.ArrayList;

public interface ReviewInquiry {

    ///Search for all reviews for a movie
    ArrayList<ReviewObject> searchMovieReviews(String movieName);

    ///Search for all reviews by a user
    ArrayList<ReviewObject> searchUserReviews(String userName);
}