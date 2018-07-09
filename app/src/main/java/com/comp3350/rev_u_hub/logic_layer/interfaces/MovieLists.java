package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.MovieObject;

import java.util.List;

public interface MovieLists {

    // View the top n movies, ordered by rating
    List<MovieObject> getMovieList(int n);

    // View the top n movies in a category, ordered by rating
    List<MovieObject> getMovieListCategory(int n, String category);
}
