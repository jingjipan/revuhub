package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNoRatingsException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieRatings;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserMovieProfile;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserMovieStats implements UserMovieProfile{

    private MovieSearch movieSearch;
    private UserSearch userSearch;
    private ReviewSearch reviewSearch;
    private MovieRatings movieRatings;

    public UserMovieStats(MovieSearch setMovieSearch,
                          UserSearch setUserSearch,
                          ReviewSearch setReviewSearch,
                          MovieRatings setMovieRatings) {
        movieSearch = setMovieSearch;
        userSearch = setUserSearch;
        reviewSearch = setReviewSearch;
        movieRatings = setMovieRatings;
    }

    // Return a list of up to n of the movies a user has reviewed
    public List<MovieObject> getReviewedList(int n, String username)
            throws ReviewDataException, UserDataNotFoundException,
            MovieDataNotFoundException {
        List<MovieObject> movieList = new ArrayList<>();
        List<ReviewObject> reviewList;
        UserObject user;
        MovieObject movie;

        user = userSearch.getUserSimple(username);
        if (user.isEmpty()) throw new UserDataNotFoundException("The user "+username+
            " does not exist.");

        reviewList = reviewSearch.getReviews(user);

        for(int i=0; i<reviewList.size(); i++) {
            movie = movieSearch.getMovieSimple(reviewList.get(i).getMovieName());
            if (movie.isEmpty()) throw new MovieDataNotFoundException("A review was found for "+
                "a non-existent movie.");
            movieList.add(movie);
        }

        Collections.sort(movieList);
        return movieList.subList(0, n);
    }

    // Return a user's longest review
    public ReviewObject getLongestReview(String username)
            throws ReviewDataException, UserDataNotFoundException {
        List<ReviewObject> reviewList;
        UserObject user;

        user = userSearch.getUserSimple(username);
        if (user.isEmpty()) throw new UserDataNotFoundException("The user "+username+
                " does not exist.");

        reviewList = reviewSearch.getReviews(user);

        Collections.sort(reviewList);
        return reviewList.get(reviewList.size()-1);
    }

    // Return the average rating of the movies a user has reviewed
    public double getReviewRatingAverage(String username)
            throws ReviewDataException, UserDataNotFoundException, MovieDataException {
        List<ReviewObject> reviewList;
        UserObject user;
        double sumRatings = 0;
        double rating;
        int notRated = 0;

        user = userSearch.getUserSimple(username);
        if (user.isEmpty()) throw new UserDataNotFoundException("The user "+username+
                " does not exist.");

        reviewList = reviewSearch.getReviews(user);

        for(int i=0; i<reviewList.size(); i++) {
            try {
                rating = movieRatings.getAverageRating(reviewList.get(i).getMovieName());
                sumRatings += rating;
            } catch (MovieDataNoRatingsException e) {
                notRated++;
            }
        }

        if (notRated==reviewList.size()) throw new MovieDataNoRatingsException("None of the "+
            "movies reviewed by this user have ratings.");

        return sumRatings/(reviewList.size()-notRated);
    }
}
