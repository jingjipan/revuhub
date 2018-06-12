package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.PersistenceLayer.PersistenceInterface;
import com.comp3350.rev_u_hub.PersistenceLayer.fakeStorage;

public class TypoEngine implements LogicInterface.MovieSearcher{

    private PersistenceInterface myPersistenceLayer;

    TypoEngine() {
        myPersistenceLayer = new fakeStorage();
    }

    TypoEngine(PersistenceInterface setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    public MovieDMObject getMovieSimple(String title) {
        return sanitizeMovie(fetchPersistent(title));
    }

    public MovieDMObject getMovie(String title) {
        MovieDMObject movieFound;

        movieFound = deletionSearch(title);
        if (movieFound==null) movieFound = transpositionSearch(title);

        if (movieFound==null) movieFound = insertionSearch(title, LogicConstants.lowercaseAlphabet);
        if (movieFound==null) movieFound = substitutionSearch(title, LogicConstants.lowercaseAlphabet);
        if (movieFound==null) movieFound = insertionSearch(title, LogicConstants.uppercaseAlphabet);
        if (movieFound==null) movieFound = substitutionSearch(title, LogicConstants.uppercaseAlphabet);
        if (movieFound==null) movieFound = insertionSearch(title, LogicConstants.numbers);
        if (movieFound==null) movieFound = substitutionSearch(title, LogicConstants.numbers);
        if (movieFound==null) movieFound = insertionSearch(title, LogicConstants.symbols);
        if (movieFound==null) movieFound = substitutionSearch(title, LogicConstants.symbols);

        return sanitizeMovie(movieFound);
    }

    private MovieDMObject fetchPersistent(String title) {
        return new MovieDMObject(myPersistenceLayer.searchMovie(title));
    }

    private MovieDMObject sanitizeMovie(MovieDMObject theMovie) {
        if (theMovie==null) return LogicConstants.noMovie;
        else if (theMovie.isEmpty()) return LogicConstants.noMovie;
        else return theMovie;
    }

    private MovieDMObject deletionSearch(String title) {
        return null;
    }

    private MovieDMObject transpositionSearch(String title) {
        return null;
    }

    private MovieDMObject insertionSearch(String title, String validChars) {
        return null;
    }

    private MovieDMObject substitutionSearch(String title, String validChars) {
        return null;
    }
}