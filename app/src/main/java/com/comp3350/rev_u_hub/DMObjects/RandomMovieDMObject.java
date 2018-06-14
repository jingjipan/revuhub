package com.comp3350.rev_u_hub.DMObjects;

import com.comp3350.rev_u_hub.LogicLayer.UnitTestHelper;
import com.comp3350.rev_u_hub.PersistenceLayer.movie;

import java.io.File;
import java.util.ArrayList;

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
        movie randMovie;
        if (allChars) {
            randMovie = new movie(
                    UnitTestHelper.randomString(length),
                    UnitTestHelper.randomString(length),
                    new String[]{UnitTestHelper.randomString(length),
                            UnitTestHelper.randomString(length),
                            UnitTestHelper.randomString(length)},
                    UnitTestHelper.randomString(length),
                    UnitTestHelper.randomStringList(3, length),
                    UnitTestHelper.randomStringList(3, length)
            );
        } else {
            randMovie = new movie(
                    UnitTestHelper.randomWord(length),
                    UnitTestHelper.randomWord(length),
                    new String[]{UnitTestHelper.randomWord(length),
                            UnitTestHelper.randomWord(length),
                            UnitTestHelper.randomWord(length)},
                    UnitTestHelper.randomWord(length),
                    UnitTestHelper.randomWordList(3, length),
                    UnitTestHelper.randomWordList(3, length)
            );
        }
        myMovie = validateMovie(randMovie);
    }
}