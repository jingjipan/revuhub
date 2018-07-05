package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.MovieObject;

public interface MovieSearch {

    //Search for a movie using a loose search
    MovieObject getMovie(String movieName);

    //Search for a movie using a strict search
    MovieObject getMovieSimple(String movieName);
}