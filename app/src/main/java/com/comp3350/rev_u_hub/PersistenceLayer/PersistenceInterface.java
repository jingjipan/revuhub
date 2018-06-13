package com.comp3350.rev_u_hub.PersistenceLayer;

public interface PersistenceInterface {
    ///Search for specific movie in the map
    movie searchMovie(String movieName);

    ///Add a new movie into the storage
    void addNewMovie(String movieName, movie m);
}