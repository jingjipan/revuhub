package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;

public interface MovieAccess {

    //Search for a user using a loose search
    MovieObject getMovie(String movieName);

    //Search for a user using a strict search
    MovieObject getMovieSimple(String movieName);

    //Add a new movie into the storage
    void addNewMovie(String movieName, MovieObject m);
}