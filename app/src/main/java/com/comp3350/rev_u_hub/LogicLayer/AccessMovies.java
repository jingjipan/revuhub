package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.PersistenceLayer.MoviePersistence;

public class AccessMovies {
    private MoviePersistence moviePersistence;
    private LogicInterface movieLogic;

    public AccessMovies()
    {
        moviePersistence = Services.getmoviePersistence();
        movieLogic = InitializeBackend.createLogicLayer(moviePersistence);
    }

    ///Search for specific movie in the map
    public MovieDMObject searchMovie(String movieName) {
        return movieLogic.getMovie(movieName);  // If the movie does not exist in the storage, a null would be returned.
    }

    ///Add a new movie into the storage
    public void addNewMovie(String movieName, MovieDMObject m) {
        moviePersistence.addNewMovie(movieName, m);
    }
}
