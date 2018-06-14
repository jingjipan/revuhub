package com.comp3350.rev_u_hub.DMObjects;

import com.comp3350.rev_u_hub.LogicLayer.UnitTestHelper;

public class RandomMovieDMObject extends MovieDMObject {

    public RandomMovieDMObject() {
        this(false,UnitTestHelper.randomInteger());
    }

    public RandomMovieDMObject(boolean allChars) {
        this(allChars,UnitTestHelper.randomInteger());
    }

    public RandomMovieDMObject(int length) {
        this(false,length);
    }

    // allChars = all characters, not just words
    public RandomMovieDMObject(boolean allChars, int length) {
        MovieDMObject randMovie;
        if (allChars) {
            randMovie = new MovieDMObject(
                    UnitTestHelper.randomString(length),
                    UnitTestHelper.randomString(length),
                    UnitTestHelper.randomString(length),
                    UnitTestHelper.randomStringList(3, length)
            );
        } else {
            randMovie = new MovieDMObject(
                    UnitTestHelper.randomWord(length),
                    UnitTestHelper.randomWord(length),
                    UnitTestHelper.randomWord(length),
                    UnitTestHelper.randomWordList(3, length)
            );
        }
        if(randMovie != null && validateMovie(randMovie)) {
            this.title = randMovie.getTitle();
            this.synopsis = randMovie.getSynopsis();
            this.cast = randMovie.getCast();
            this.reviews = randMovie.getReviews();
        } // else return null?

    }
}