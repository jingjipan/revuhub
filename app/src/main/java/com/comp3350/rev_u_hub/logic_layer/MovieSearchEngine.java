package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub.persistence_layer.stubs.MoviePersistenceStub;

public class MovieSearchEngine implements MovieAccess {

    public static final String lowercaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final String uppercaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String numbers = "0123456789";
    public static final String symbols = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static final String allChars = "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    private MoviePersistence myPersistenceLayer;

    public MovieSearchEngine() {
        myPersistenceLayer = new MoviePersistenceStub();
    }

    public MovieSearchEngine(MoviePersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    public void addNewMovie(String movieName, MovieObject m) {
        myPersistenceLayer.addNewMovie(movieName, m);
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
            movieFound = insertionSearch(title, lowercaseAlphabet);
        if (isEmpty(movieFound))
            movieFound = substitutionSearch(title, lowercaseAlphabet);
        if (isEmpty(movieFound))
            movieFound = insertionSearch(title, uppercaseAlphabet);
        if (isEmpty(movieFound))
            movieFound = substitutionSearch(title, uppercaseAlphabet);
        if (isEmpty(movieFound))
            movieFound = insertionSearch(title, numbers);
        if (isEmpty(movieFound))
            movieFound = substitutionSearch(title, numbers);
        if (isEmpty(movieFound))
            movieFound = insertionSearch(title, symbols);
        if (isEmpty(movieFound))
            movieFound = substitutionSearch(title, symbols);

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