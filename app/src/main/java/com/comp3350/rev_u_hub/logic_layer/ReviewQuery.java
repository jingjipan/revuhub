package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewInfo;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;

import java.util.ArrayList;
import java.util.List;

public class ReviewQuery implements ReviewInfo {
    private ReviewPersistence myPersistenceLayer;

    public ReviewQuery(ReviewPersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    //Search for reviews of a movie
    //throws ReviewDataException
    public List<ReviewObject> getReviews(MovieObject movie) {
        return myPersistenceLayer.getReviewsOfMovie(movie.getTitle());
    }

    //Search for reviews in text form of a movie
    //throws ReviewDataException
    public List<String> getReviewsText(MovieObject movie) {
        List<String> stringList = new ArrayList<>();
        List<ReviewObject> reviewList = getReviews(movie);

        for(int i=0; i<reviewList.size(); i++) {
            stringList.add(reviewList.get(i).toString());
        }
        return stringList;
    }

    //Search for reviews by a user
    //throws ReviewDataException
    public List<ReviewObject> getReviews(UserObject user){
        return myPersistenceLayer.getReviewsOfMovie(user.getUserName());
    }

    //Search for reviews in text form by a user
    //throws ReviewDataException
    public List<String> getReviewsText(UserObject user) {
        List<String> stringList = new ArrayList<>();
        List<ReviewObject> reviewList = getReviews(user);

        for(int i=0; i<reviewList.size(); i++) {
            stringList.add(reviewList.get(i).toString());
        }
        return stringList;
    }

    //throws ReviewDataNoMovieException
    public boolean hasReview(MovieObject movie) {
        return !myPersistenceLayer.getReviewsOfMovie(movie.getTitle()).isEmpty();
    }

    //throws ReviewDataNoMovieException
    public double getAverageRating(MovieObject movie) {
        return movie.getRating();
    }

    //throws ReviewDataNoMovieException
    public int getReviewCount(MovieObject movie) {
        return movie.getCount();
    }
}