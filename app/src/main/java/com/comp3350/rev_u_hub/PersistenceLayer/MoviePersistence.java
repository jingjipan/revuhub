package com.comp3350.rev_u_hub.PersistenceLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;

public interface MoviePersistence {

    void addStaticInfo();

    ///Search for specific movie in the map
    MovieDMObject searchMovie(String movieName);

    ///Add a new movie into the storage
    void addNewMovie(String movieName, MovieDMObject m);
}
