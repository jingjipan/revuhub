package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.SearchableObject;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;

import java.util.List;

public class MovieSearchEngine extends SearchEngine implements MovieSearch {
    private MoviePersistence myPersistenceLayer;

    public MovieSearchEngine(MoviePersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    public MovieObject getMovieSimple(String movieName) {
        return (MovieObject) getObjectSimple(movieName);
    }

    // Uses Damerau–Levenshtein_distance 1 permutations of a title to search
    // https://en.wikipedia.org/wiki/Damerau–Levenshtein_distance
    public MovieObject getMovie(String movieName) {
        return (MovieObject) getObject(movieName);
    }

    // Required to allow SearchEngine methods to search the persistence layer
    protected SearchableObject fetchPersistent(String searchText) {
        MovieObject movie;
        List<MovieObject> list = myPersistenceLayer.searchMovie(searchText);
        if (list.isEmpty()) movie = (MovieObject) defaultObject();
        else movie = list.get(0);
        return movie;
    }

    // Required to tell SearchEngine what to return if the search fails
    protected SearchableObject defaultObject() {
        return new MovieObject();
    }
}