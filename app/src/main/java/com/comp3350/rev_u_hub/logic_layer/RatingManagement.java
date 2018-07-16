package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataInvalidRatingException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNoRatingsException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieRatings;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;

public class RatingManagement implements MovieRatings {

    private MovieSearchValidator myMovieSearchValidator;
    private MoviePersistence myPersistenceLayer;

    public RatingManagement(MovieSearch setMovieSearch, MoviePersistence setPersistenceLayer) {
        myMovieSearchValidator = new MovieSearchValidator(setMovieSearch);
        myPersistenceLayer = setPersistenceLayer;
    }

    // Returns true if a movie has any reviews
    public boolean hasRating(String title) throws MovieDataNotFoundException {
        MovieObject movie = myMovieSearchValidator.getMovie(title);
        return movie.getCount()>0;
    }

    // Returns a movie's average review rating
    public double getAverageRating(String title) throws MovieDataException {
        MovieObject movie = myMovieSearchValidator.getMovie(title);

        if (movie.getCount()==0)
            throw new MovieDataNoRatingsException("The selected movie does not have any ratings.");
        return movie.getRating();
    }

    // Returns the number of reviews a movie has
    public int getRatingCount(String title) throws MovieDataNotFoundException {
        MovieObject movie = myMovieSearchValidator.getMovie(title);
        return movie.getCount();
    }

    // Adds a rating to a movie
    public double addRating(String title, int rating) throws MovieDataException {
        MovieObject movie = myMovieSearchValidator.getMovie(title);
        if (rating<1 || rating>5)
            throw new MovieDataInvalidRatingException("Reviews must be an integer from 1 to 5.");

        movie.updateRating((double) rating);
        myPersistenceLayer.updateMovie(movie);
        return movie.getCount();
    }
}