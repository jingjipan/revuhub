package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieObject;
import com.comp3350.rev_u_hub.DMObjects.ReviewObject;

import java.util.ArrayList;

public interface LogicInterface extends MovieInfoGetter, ReviewProcessor, MovieSearcher {}

interface MovieInfoGetter extends LogicComponent {
    //As a user, I want to be able to view the synopsis of a movie. (high)
    String getSynopsis(MovieObject movie);

    //As a user, I want to be able to view the casts of a movie. (high)
    ArrayList<String> getCast(MovieObject movie);
}

interface ReviewProcessor extends LogicComponent {
    //As a user, I want to be able to view reviews about a movie. (high)
    ReviewObject getReview(MovieObject movie);

    //As a user, I want to be able to rate reviews about a movie. (high)
    boolean setReview(MovieObject movie, ReviewObject review);
    boolean setReview(MovieObject movie, String reviewText);
}

interface MovieSearcher extends LogicComponent{
    //As a user, I want to be able to search movies based on title. (high)
    MovieObject getMovie(String title);
    MovieObject getMovieSimple(String title);
}

interface LogicComponent {}