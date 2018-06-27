package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieObject;
import com.comp3350.rev_u_hub.DMObjects.ReviewObject;

public class ReviewManager implements ReviewProcessor {

    public ReviewManager() {}

    public ReviewObject getReview(MovieObject movie) {
        return InfoFormatConverter.convertToReviewDMObject(movie.getReviews());
    }

    public boolean setReview(MovieObject movie, ReviewObject review) {
        movie.setReviews(review);
        return movie.hasReviews();
    }

    public boolean setReview(MovieObject movie, String reviewText) {
        movie.setReviews(reviewText);
        return movie.hasReviews();
    }
}