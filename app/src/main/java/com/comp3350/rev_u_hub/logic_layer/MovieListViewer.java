package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieLists;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieListViewer implements MovieLists {
    private MoviePersistence myPersistenceLayer;

    public MovieListViewer(MoviePersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    // Return the top n movies, ordered by rating
    public List<MovieObject> getMovieList(int n) throws MovieDataNotFoundException{
        List<MovieObject> movieList = new ArrayList<>();
        List<MovieObject> retrievedList = myPersistenceLayer.getMovieSequential();

        movieList.addAll(retrievedList);

        if (movieList.isEmpty()) throw new MovieDataNotFoundException("No movies were found.");

        Collections.sort(movieList);
        return movieList.subList(0, n);
    }

    // Return the top n movies in a category, ordered by rating
    public List<MovieObject> getMovieListCategory(int n, String category)
            throws MovieDataNotFoundException {
        List <MovieObject> movieList;

        try {
            movieList = getMovieList(n);
        } catch (MovieDataNotFoundException e) {
            throw new MovieDataNotFoundException("No movies were found for category "+category+".");
        }
        return movieList;
    }
}