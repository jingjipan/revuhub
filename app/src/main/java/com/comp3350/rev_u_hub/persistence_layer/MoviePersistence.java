package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;

public interface MoviePersistence {

    ///Search for specific movie in the map
    MovieObject searchMovie(String movieName);

    ///Add a new movie into the storage
    MovieObject addNewMovie(MovieObject m);

    ///Update the stored copy of a movie
    MovieObject updateMovie(MovieObject m);

    ///Delete the stored copy of a movie
    void deleteMovie(MovieObject m);
}