package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieObject;
import com.comp3350.rev_u_hub.PersistenceLayer.MoviePersistence;
import com.comp3350.rev_u_hub.PersistenceLayer.stubs.MoviePersistenceStub;

public class TypoEngine implements MovieSearcher{

    private MoviePersistence myPersistenceLayer;

    TypoEngine() {
        myPersistenceLayer = new MoviePersistenceStub();
    }

    TypoEngine(MoviePersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    public MovieObject getMovieSimple(String title) {
        return sanitizeMovie(fetchPersistent(title));
    }

    // Uses Damerau–Levenshtein_distance 1 permutations of a title to search
    // https://en.wikipedia.org/wiki/Damerau–Levenshtein_distance
    public MovieObject getMovie(String title) {
        MovieObject movieFound;

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

    private MovieObject fetchPersistent(String title) {
        return new MovieObject(myPersistenceLayer.searchMovie(title));
    }

    private MovieObject sanitizeMovie(MovieObject theMovie) {
        if (theMovie==null) return new MovieObject();
        else if (theMovie.isEmpty()) return new MovieObject();
        else return theMovie;
    }

    private boolean isEmpty(MovieObject theMovie) {
        return theMovie==null || theMovie.isEmpty();
    }

    // removing one character in the title
    private MovieObject deletionSearch(String title) {
        MovieObject movieFound = null;
        String attempt;

        for (int i=0; i<title.length() && isEmpty(movieFound); i++) {
            attempt = title.substring(0,i) + title.substring(i+1,title.length());
            movieFound = fetchPersistent(attempt);
        }

        return movieFound;
    }

    // swapping two adjacent characters in the title
    private MovieObject transpositionSearch(String title) {
        MovieObject movieFound = null;
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
    private MovieObject insertionSearch(String title, String validChars) {
        MovieObject movieFound = null;
        String attempt;

        for (int i=0; i<=title.length() && isEmpty(movieFound); i++) {

            for (int j=0; j<validChars.length() && isEmpty(movieFound); j++) {
                attempt = title.substring(0, i) +
                        validChars.charAt(j) +
                        title.substring(i, title.length());
                movieFound = fetchPersistent(attempt);
            }
        }

        return movieFound;
    }

    // changing one character in the title
    private MovieObject substitutionSearch(String title, String validChars) {
        MovieObject movieFound = null;
        String attempt;

        for (int i=0; i<title.length() && isEmpty(movieFound); i++) {

            for (int j=0; j<validChars.length() && isEmpty(movieFound); j++) {
                attempt = title.substring(0, i) +
                        validChars.charAt(j) +
                        title.substring(i+1, title.length());
                movieFound = fetchPersistent(attempt);
            }
        }

        return movieFound;
    }
}