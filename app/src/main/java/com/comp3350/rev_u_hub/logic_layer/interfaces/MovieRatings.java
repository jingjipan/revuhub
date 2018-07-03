package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;

public interface MovieRatings {

    // Returns a movie's average review rating
    double getAverageRating(String title) throws MovieDataException;

    // Returns the number of reviews a movie has
    int getRatingCount(String title) throws MovieDataNotFoundException;

    // Returns true if a movie has any reviews
    boolean hasRating(String title) throws MovieDataNotFoundException;

    // Adds a rating to a movie
    double addRating(String title, int rating) throws MovieDataException;
}