package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;

import java.util.List;

public interface MovieLists {

    // Return the top n movies, ordered by rating
    List<MovieObject> getMovieList(int n) throws MovieDataNotFoundException;

    // Return the top n movies in a category, ordered by rating
    List<MovieObject> getMovieListCategory(int n, String category)
            throws MovieDataNotFoundException;
}
