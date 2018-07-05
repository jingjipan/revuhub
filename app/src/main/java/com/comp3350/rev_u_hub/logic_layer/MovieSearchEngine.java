package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.SearchableObject;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchEngine extends SearchEngine implements MovieSearch {
    private MoviePersistence myPersistenceLayer;
    private List<MovieObject> lastRetrieval;
    private List<String> lastRetrievalStrings;

    public MovieSearchEngine(MoviePersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    public MovieObject getMovieSimple(String movieName) {
        retrieveFromPersistence();
        return (MovieObject) getObjectSimple(movieName);
    }

    // Uses Damerau–Levenshtein_distance 1 permutations of a title to search
    // https://en.wikipedia.org/wiki/Damerau–Levenshtein_distance
    public MovieObject getMovie(String movieName) {
        retrieveFromPersistence();
        return (MovieObject) getObject(movieName);
    }

    // Required to allow SearchEngine methods to search the persistence layer
    protected SearchableObject fetchPersistent(String searchText) {
        MovieObject movie = (MovieObject) defaultObject();
        for(int i=0; i<lastRetrievalStrings.size(); i++) {
            if (lastRetrievalStrings.get(i).equals(searchText))
                movie = lastRetrieval.get(i);
        }
        return movie;
    }

    // Required to tell SearchEngine what to return if the search fails
    protected SearchableObject defaultObject() {
        return new MovieObject();
    }

    private void retrieveFromPersistence() {
        lastRetrievalStrings =  new ArrayList<>();
        lastRetrieval = myPersistenceLayer.getMovieSequential();
        for(int i=0; i<lastRetrieval.size(); i++) {
            lastRetrievalStrings.add(lastRetrieval.get(i).getTitle());
        }
    }
}