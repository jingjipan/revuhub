package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.DMObjects.ReviewDMObject;

import java.util.ArrayList;
import java.io.File;

public interface LogicInterface {
    //As a user, I want to be able to view the synopsis of a movie. (high)
    public String getSynopsis(MovieDMObject movie);

    //As a user, I want to be able to view the casts of a movie. (high)
    public ArrayList<String> getCast(MovieDMObject movie);

    //As a user, I want to be able to view the photos of a movie. (high)
    public File getPhoto(MovieDMObject movie); //returns file path to movie

    //As a user, I want to be able to view the news of a movie. (high)
    public String getNews(MovieDMObject movie);

    //As a user, I want to be able to view reviews about a movie. (high)
    public ReviewDMObject getReview(MovieDMObject movie);

    //As a user, I want to be able to rate reviews about a movie. (high)
    public boolean setReview(MovieDMObject movie, ReviewDMObject review);

    //As a user, I want to be able to search movies based on title. (high)
    public MovieDMObject getMovie(String title);
}
