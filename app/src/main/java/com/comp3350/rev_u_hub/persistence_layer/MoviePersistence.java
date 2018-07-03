package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;

import java.util.List;

public interface MoviePersistence {

    ///Get all of the movies in the DB
    List<MovieObject> getMovieSequential();

    ///Add a new movie into the DB
    MovieObject addNewMovie(MovieObject newMovie);

    ///Search movie by movie's name
    List<MovieObject> searchMovie(String movieName);

    ///Update a movie's info in the DB
    MovieObject updateMovie(MovieObject movie);

    ///Delete a movie's info from the DB
    void deleteMovie(MovieObject movie);
}