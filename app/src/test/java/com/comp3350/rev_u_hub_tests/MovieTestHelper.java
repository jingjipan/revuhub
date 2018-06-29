package com.comp3350.rev_u_hub_tests;

import com.comp3350.rev_u_hub.data_objects.MovieObject;

import java.util.ArrayList;
import java.util.Random;

public abstract class MovieTestHelper extends UnitTestHelper {

    public static final String[] TEST_DESCRIPTORS = {"a random movie",
            "a random movie with numbers and symbols",
            "no movie",
            "a random movie with zero length contents",
            "a random movie with short contents",
            "a random movie with long contents"};

    public static MovieObject randomMovie() {
        return randomMovieObject();
    }

    // allChars = all characters, not just words
    public static MovieObject randomMovie(boolean allChars) {
        return randomMovieObject(allChars);
    }

    public static MovieObject randomMovie(int length) {
        return randomMovieObject(length);
    }

    // allChars = all characters, not just words
    public static MovieObject randomMovie(boolean allChars, int length) {
        return randomMovieObject(allChars, length);
    }

    public static MovieObject[] getMovieTestArray() {
        return new MovieObject[]{
                MovieTestHelper.randomMovie(true),
                MovieTestHelper.randomMovie(true),
                new MovieObject(),
                MovieTestHelper.randomMovie(true,0),
                MovieTestHelper.randomMovie(true,1),
                MovieTestHelper.randomMovie(true,168)
        };
    }

    public static MovieObject randomMovieObject() {
        return randomMovieObject(false, MovieTestHelper.randomInteger());
    }

    public static MovieObject randomMovieObject(boolean allChars) {
        return randomMovieObject(allChars, MovieTestHelper.randomInteger());
    }

    public static MovieObject randomMovieObject(int length) {
        return randomMovieObject(false,length);
    }

    // allChars = all characters, not just words
    public static MovieObject randomMovieObject(boolean allChars, int length) {
        MovieObject randMovie;
        if (allChars) {
            randMovie = new MovieObject(
                    randomString(length),
                    randomString(length),
                    randomString(length),
                    randomInteger(1, 100),
                    randomInteger(1, 200)/20.0
            );
        } else {
            randMovie = new MovieObject(
                    randomWord(length),
                    randomWord(length),
                    randomWord(length),
                    randomInteger(1, 100),
                    randomInteger(1, 200)/20.0
            );
        }
        return randMovie.validateMovie(randMovie) ? randMovie : new MovieObject();
    }
}