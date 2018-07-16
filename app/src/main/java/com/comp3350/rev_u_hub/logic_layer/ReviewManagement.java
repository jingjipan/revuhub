package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationFailedException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationNoUserException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNoMovieException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNoUserException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataWrongUserException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;

public class ReviewManagement implements ReviewManager {

    private ReviewPersistence myPersistenceLayer;
    private ReviewSearch reviewSearch;
    private MovieSearch movieSearch;
    private UserSearch userSearch;
    private UserLogin userLogin;

    public ReviewManagement(ReviewPersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
        reviewSearch = Services.getReviewSearch();
        movieSearch = Services.getMovieSearch();
        userSearch = Services.getUserSearch();
        userLogin = Services.getUserLogin();
    }

    public ReviewManagement(ReviewPersistence setPersistenceLayer,
                            ReviewSearch setReviewSearch,
                            MovieSearch setMovieSearch,
                            UserSearch setUserSearch,
                            UserLogin setUserLogin) {
        myPersistenceLayer = setPersistenceLayer;
        reviewSearch = setReviewSearch;
        movieSearch = setMovieSearch;
        userSearch = setUserSearch;
        userLogin = setUserLogin;
    }

    // Creates and stores a review if its parameters are valid
    public ReviewObject createReview(String setReview, String movieName, String userName)
            throws ReviewCreationException {
        ReviewObject review;
        UserObject user;
        MovieObject movie;

        try {
            user = getUserObject(userName);
        } catch (ReviewDataException e) {
            throw new ReviewCreationFailedException(e.getMessage());
        }

        try {
            if (!user.equals(userLogin.getUser()))
                throw new ReviewCreationNoUserException("The selected user is not logged in.");
        } catch (UserDataNotFoundException e) {
            throw new ReviewCreationNoUserException("No user is currently logged in.");
        }

        try {
            movie = getMovieObject(movieName);
        } catch (ReviewDataNoMovieException e) {
            throw new ReviewCreationFailedException(e.getMessage());
        }

        if (reviewSearch.reviewExists(movieName, userName))
            throw new ReviewCreationDuplicateException("A review by the selected user for the "
                    + "selected movie already exists.");

        review = new ReviewObject(setReview,movieName,userName);
        myPersistenceLayer.addNewReview(review);

        try {
            review = reviewSearch.getReview(movie, user);
        } catch (ReviewDataNotFoundException e) {
            throw new ReviewCreationFailedException("The review could not be created.");
        }

        return review;
    }

    // Modifies a stored review's text
    public ReviewObject editReview(String reviewName, String movieName, String userName)
            throws ReviewDataException {
        ReviewObject review;
        UserObject user;
        MovieObject movie;

        user = getUserObject(userName);

        try {
            if (!user.equals(userLogin.getUser()))
                throw new ReviewDataWrongUserException("The selected user is not logged in.");
        } catch (UserDataNotFoundException e) {
            throw new ReviewDataNoUserException("No user is currently logged in.");
        }

        movie = getMovieObject(movieName);

        review = reviewSearch.getReview(movie, user);

        review.setReview(reviewName);
        myPersistenceLayer.updateReview(review);

        return review;
    }

    // Removes a review if it exists
    public void removeReview(ReviewObject review) throws ReviewDataException {
        myPersistenceLayer.deleteReview(reviewSearch.getReview(review.getMovieName(),
                review.getUserName()));
    }

    public MovieObject getMovieObject(String title) throws ReviewDataNoMovieException {
        MovieObject movie = movieSearch.getMovieSimple(title);

        if (movie==null || movie.isEmpty())
            throw new ReviewDataNoMovieException("The selected movie does not exist.");
        return movie;
    }

    public UserObject getUserObject(String userName) throws ReviewDataException {
        UserObject user = userSearch.getUserSimple(userName);

        if (user==null || user.isEmpty())
            throw new ReviewDataNoUserException("The selected user does not exist.");
        return user;
    }
}
