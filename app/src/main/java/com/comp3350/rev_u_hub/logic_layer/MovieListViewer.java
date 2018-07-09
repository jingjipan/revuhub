package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieLists;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;

import java.util.Collections;
import java.util.List;

public class MovieListViewer implements MovieLists {
    private MoviePersistence myPersistenceLayer;

    public MovieListViewer(MoviePersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    // View the top n movies, ordered by rating
    public List<MovieObject> getMovieList(int n) {
        List<MovieObject> movieList = myPersistenceLayer.getMovieSequential();
        Collections.sort(movieList);
        return movieList.subList(0, n);
    }

    // View the top n movies in a category, ordered by rating
    public List<MovieObject> getMovieListCategory(int n, String category) {
        return getMovieList(n);
    }
}