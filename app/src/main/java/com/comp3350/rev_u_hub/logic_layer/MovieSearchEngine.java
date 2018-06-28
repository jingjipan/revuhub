package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.SearchableObject;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub.persistence_layer.stubs.MovieHSQLDB;

public class MovieSearchEngine extends SearchEngine implements MovieAccess {
    private MoviePersistence myPersistenceLayer;

    public MovieSearchEngine() {
        myPersistenceLayer = new MovieHSQLDB();
    }

    public MovieSearchEngine(MoviePersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    public void addNewMovie(String movieName, MovieObject m) {
        myPersistenceLayer.addNewMovie(movieName, m);
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
        return new MovieObject(myPersistenceLayer.searchMovie(searchText));
    }

    // Required to tell SearchEngine what to return if the search fails
    protected SearchableObject defaultObject() {
        return new MovieObject();
    }
}