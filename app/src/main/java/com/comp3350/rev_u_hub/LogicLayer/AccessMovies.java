package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.PersistenceLayer.MoviePersistence;
import com.comp3350.rev_u_hub.PersistenceLayer.movie;
import com.comp3350.rev_u_hub.application.Services;

public class AccessMovies {
    private MoviePersistence moviePersistence;

    public AccessMovies()
    {
        moviePersistence = Services.getmoviePersistence();
    }

    public movie searchMovie(String movieName) {
        return moviePersistence.searchMovie(movieName);  //If the movie does not exist in the storage, a null would be returned.
    }

    public void addNewMovie(String movieName, movie m) {
        moviePersistence.addNewMovie(movieName, m);
    }
}
