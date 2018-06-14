package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.DMObjects.ReviewDMObject;

public class ReviewManager implements ReviewProcessor {

    public ReviewManager() {}

    public ReviewDMObject getReview(MovieDMObject movie) {
        return InfoFormatConverter.convertToReviewDMObject(movie.getReviews());
    }

    public boolean setReview(MovieDMObject movie, ReviewDMObject review) {
        movie.setReviews(review);
        return movie.hasReviews();
    }

    public boolean setReview(MovieDMObject movie, String reviewText) {
        movie.setReviews(reviewText);
        return movie.hasReviews();
    }
}