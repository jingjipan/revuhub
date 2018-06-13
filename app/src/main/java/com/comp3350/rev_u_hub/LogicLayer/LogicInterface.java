package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.DMObjects.ReviewDMObject;

import java.util.ArrayList;
import java.io.File;

public interface LogicInterface extends MovieInfoGetter, ReviewProcessor, MovieSearcher {

}

interface MovieInfoGetter {
    //As a user, I want to be able to view the synopsis of a movie. (high)
    String getSynopsis(MovieDMObject movie);

    //As a user, I want to be able to view the casts of a movie. (high)
    ArrayList<String> getCast(MovieDMObject movie);

    //As a user, I want to be able to view the photos of a movie. (high)
    File getPhoto(MovieDMObject movie); //returns file path to movie

    //As a user, I want to be able to view the news of a movie. (high)
    String getNews(MovieDMObject movie);
}

interface ReviewProcessor {
    //As a user, I want to be able to view reviews about a movie. (high)
    ReviewDMObject getReview(MovieDMObject movie);

    //As a user, I want to be able to rate reviews about a movie. (high)
    boolean setReview(MovieDMObject movie, ReviewDMObject review);
    boolean setReview(MovieDMObject movie, String reviewText);
}

interface MovieSearcher {
    //As a user, I want to be able to search movies based on title. (high)
    MovieDMObject getMovie(String title);
    MovieDMObject getMovieSimple(String title);
}