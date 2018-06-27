package com.comp3350.rev_u_hub.DMObjects;

import com.comp3350.rev_u_hub.LogicLayer.UnitTestHelper;

public class RandomMovieObject extends MovieObject {

    public RandomMovieObject() {
        this(false,UnitTestHelper.randomInteger());
    }

    public RandomMovieObject(boolean allChars) {
        this(allChars,UnitTestHelper.randomInteger());
    }

    public RandomMovieObject(int length) {
        this(false,length);
    }

    // allChars = all characters, not just words
    public RandomMovieObject(boolean allChars, int length) {
        MovieObject randMovie;
        if (allChars) {
            randMovie = new MovieObject(
                    UnitTestHelper.randomString(length),
                    UnitTestHelper.randomString(length),
                    UnitTestHelper.randomString(length),
                    UnitTestHelper.randomStringList(3, length)
            );
        } else {
            randMovie = new MovieObject(
                    UnitTestHelper.randomWord(length),
                    UnitTestHelper.randomWord(length),
                    UnitTestHelper.randomWord(length),
                    UnitTestHelper.randomWordList(3, length)
            );
        }
        if(validateMovie(randMovie)) {
            this.title = randMovie.getTitle();
            this.synopsis = randMovie.getSynopsis();
            this.cast = randMovie.getCast();
            this.reviews = randMovie.getReviews();
        } // else return null?

    }
}