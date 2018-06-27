package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;

public interface MoviePersistence {

    void addStaticInfo();

    ///Search for specific movie in the map
    MovieObject searchMovie(String movieName);

    ///Add a new movie into the storage
    void addNewMovie(String movieName, MovieObject m);
}
