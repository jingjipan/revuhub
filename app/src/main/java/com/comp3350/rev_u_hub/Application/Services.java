package com.comp3350.rev_u_hub.Application;

import com.comp3350.rev_u_hub.logic_layer.MovieAccess;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub.persistence_layer.stubs.MovieHSQLDB;

public class Services {

    private static MoviePersistence moviePersistence = null;

    public static MoviePersistence getMoviePersistence()
    {
        if (moviePersistence == null)
        {
            moviePersistence = new MovieHSQLDB();
        }

        return moviePersistence;
    }

    public static MovieAccess getMovieAccess() {
        return new MovieSearchEngine();
    }

    public static MovieAccess getMovieAccess(MoviePersistence persistenceLayer) {
        return new MovieSearchEngine(persistenceLayer);
    }
}
