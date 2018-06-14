package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.PersistenceLayer.MoviePersistence;
import com.comp3350.rev_u_hub.Application.Services;

public class AccessMovies {
    private MoviePersistence moviePersistence;

    public AccessMovies()
    {
        moviePersistence = Services.getmoviePersistence();
    }

    ///Search for specific movie in the map
    public MovieDMObject searchMovie(String movieName) {
        return moviePersistence.searchMovie(movieName);  // If the movie does not exist in the storage, a null would be returned.
    }

    ///Add a new movie into the storage
    public void addNewMovie(String movieName, MovieDMObject m) {
        moviePersistence.addNewMovie(movieName, m);
    }
}
