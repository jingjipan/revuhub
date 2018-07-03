package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNoMovieException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNoUserException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewInfo;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;

import java.util.ArrayList;
import java.util.List;

public class ReviewQuery implements ReviewInfo {
    private ReviewPersistence myPersistenceLayer;

    public ReviewQuery(ReviewPersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    // Search for reviews of a movie
    public List<ReviewObject> getReviews(MovieObject movie) throws ReviewDataException {
        List<ReviewObject> reviewList;

        if (movie==null || movie.isEmpty())
            throw new ReviewDataNoMovieException("The selected movie does not exist.");

        reviewList = myPersistenceLayer.getReviewsOfMovie(movie.getTitle());
        if (reviewList.isEmpty())
            throw new ReviewDataNotFoundException("No reviews exist for the selected movie.");

        return reviewList;
    }

    // Search for reviews in text form of a movie
    public List<String> getReviewsText(MovieObject movie) throws ReviewDataException {
        List<String> stringList = new ArrayList<>();
        List<ReviewObject> reviewList = getReviews(movie);

        for(int i=0; i<reviewList.size(); i++) {
            stringList.add(reviewList.get(i).toString());
        }
        return stringList;
    }

    // Search for reviews by a user
    public List<ReviewObject> getReviews(UserObject user) throws ReviewDataException {
        List<ReviewObject> reviewList;

        if (user==null || user.isEmpty())
            throw new ReviewDataNoUserException("The selected user does not exist.");

        reviewList = myPersistenceLayer.getReviewsOfUser(user.getUserName());
        if (reviewList.isEmpty())
            throw new ReviewDataNotFoundException("No reviews exist for the selected user.");

        return reviewList;
    }

    // Search for reviews in text form by a user
    public List<String> getReviewsText(UserObject user) throws ReviewDataException {
        List<String> stringList = new ArrayList<>();
        List<ReviewObject> reviewList = getReviews(user);

        for(int i=0; i<reviewList.size(); i++) {
            stringList.add(reviewList.get(i).toString());
        }
        return stringList;
    }

    // Returns true if a movie has any reviews
    public boolean hasReview(MovieObject movie) throws ReviewDataNoMovieException {
        if (movie==null || movie.isEmpty())
            throw new ReviewDataNoMovieException("The selected movie does not exist.");
        return !myPersistenceLayer.getReviewsOfMovie(movie.getTitle()).isEmpty();
    }

    // Returns a movie's average review rating
    public double getAverageRating(MovieObject movie) throws ReviewDataNoMovieException {
        if (movie==null || movie.isEmpty())
            throw new ReviewDataNoMovieException("The selected movie does not exist.");
        return movie.getRating();
    }

    // Returns the number of reviews a movie has
    public int getReviewCount(MovieObject movie) throws ReviewDataNoMovieException {
        if (movie==null || movie.isEmpty())
            throw new ReviewDataNoMovieException("The selected movie does not exist.");
        return movie.getCount();
    }
}