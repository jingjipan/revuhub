package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.ReviewObject;

import java.util.List;

public interface ReviewPersistence {

    ///get all of the reviews in the database
    List<ReviewObject> getReviewsSequential();

    ///Search review of specific user for specific movie
    List<ReviewObject> searchReview(String userName, String movieName);

    ///Search review of specific user
    List<ReviewObject> getReviewsOfUser(String userName);

    ///Search review of specific movie
    List<ReviewObject> getReviewsOfMovie(String movieName);

    ///Add new review
    ReviewObject addNewReview(ReviewObject newReview);

    ///Update an old review
    ReviewObject updateReview(ReviewObject review);

    ///Delete an review
    void deleteReview(ReviewObject review);

    ///Delete all reviews of a user
    void deleteAllReview(String userName);
}