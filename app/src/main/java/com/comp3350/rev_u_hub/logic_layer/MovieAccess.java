package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;

public interface MovieAccess {
    //As a user, I want to be able to search movies based on title. (high)
    MovieObject getMovie(String title);
    MovieObject getMovieSimple(String title);

    //Add a new movie into the storage
    void addNewMovie(String movieName, MovieObject m);
}
