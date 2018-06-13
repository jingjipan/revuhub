package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.PersistenceLayer.PersistenceInterface;
import com.comp3350.rev_u_hub.PersistenceLayer.fakeStorage;

public class TypoEngine implements MovieSearcher{

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

    // Uses Damerau–Levenshtein_distance 1 permutations of a title to search
    // https://en.wikipedia.org/wiki/Damerau–Levenshtein_distance
    public MovieDMObject getMovie(String title) {
        MovieDMObject movieFound;

        movieFound = deletionSearch(title);
        if (isEmpty(movieFound)) movieFound = transpositionSearch(title);

        if (isEmpty(movieFound))
            movieFound = insertionSearch(title, LogicConstants.lowercaseAlphabet);
        if (isEmpty(movieFound))
            movieFound = substitutionSearch(title, LogicConstants.lowercaseAlphabet);
        if (isEmpty(movieFound))
            movieFound = insertionSearch(title, LogicConstants.uppercaseAlphabet);
        if (isEmpty(movieFound))
            movieFound = substitutionSearch(title, LogicConstants.uppercaseAlphabet);
        if (isEmpty(movieFound))
            movieFound = insertionSearch(title, LogicConstants.numbers);
        if (isEmpty(movieFound))
        movieFound = substitutionSearch(title, LogicConstants.numbers);
        if (isEmpty(movieFound))
            movieFound = insertionSearch(title, LogicConstants.symbols);
        if (isEmpty(movieFound))
            movieFound = substitutionSearch(title, LogicConstants.symbols);

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

    private boolean isEmpty(MovieDMObject theMovie) {
        return theMovie==null || theMovie.isEmpty();
    }

    // removing one character in the title
    private MovieDMObject deletionSearch(String title) {
        MovieDMObject movieFound = null;
        String attempt;

        for (int i=0; i<title.length() && isEmpty(movieFound); i++) {
            attempt = title.substring(0,i) + title.substring(i+1,title.length());
            movieFound = fetchPersistent(attempt);
        }

        return movieFound;
    }

    // swapping two adjacent characters in the title
    private MovieDMObject transpositionSearch(String title) {
        MovieDMObject movieFound = null;
        String attempt;

        for (int i=0; i<title.length()-1 && isEmpty(movieFound); i++) {
            attempt = title.substring(0,i) +
                    title.substring(i+1,i+2) + title.substring(i,i+1) +
                    title.substring(i+2,title.length());
            movieFound = fetchPersistent(attempt);
        }

        return movieFound;
    }

    // inserting one character somewhere in the title
    private MovieDMObject insertionSearch(String title, String validChars) {
        MovieDMObject movieFound = null;
        String attempt;

        for (int i=0; i<=title.length() && isEmpty(movieFound); i++) {

            for (int j=0; i<validChars.length() && isEmpty(movieFound); j++) {
                attempt = title.substring(0, i) +
                        validChars.charAt(j) +
                        title.substring(i, title.length());
                movieFound = fetchPersistent(attempt);
            }
        }

        return movieFound;
    }

    // changing one character in the title
    private MovieDMObject substitutionSearch(String title, String validChars) {
        MovieDMObject movieFound = null;
        String attempt;

        for (int i=0; i<title.length() && isEmpty(movieFound); i++) {

            for (int j=0; i<validChars.length() && isEmpty(movieFound); j++) {
                attempt = title.substring(0, i) +
                        validChars.charAt(j) +
                        title.substring(i+1, title.length());
                movieFound = fetchPersistent(attempt);
            }
        }

        return movieFound;
    }
}