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
        if (allChars) {
            ArrayList<String> cast = new ArrayList<>();
            cast.add(UnitTestHelper.randomString(length));
            cast.add(UnitTestHelper.randomString(length));
            cast.add(UnitTestHelper.randomString(length));

            myMovie = new movie(
                    UnitTestHelper.randomString(length),
                    UnitTestHelper.randomString(length),
                    new String[]{UnitTestHelper.randomString(length),
                            UnitTestHelper.randomString(length),
                            UnitTestHelper.randomString(length)},
                    cast
            );
        } else {
            ArrayList<String> cast = new ArrayList<>();
            cast.add(UnitTestHelper.randomWord(length));
            cast.add(UnitTestHelper.randomWord(length));
            cast.add(UnitTestHelper.randomWord(length));

            myMovie = new movie(
                    UnitTestHelper.randomWord(length),
                    UnitTestHelper.randomWord(length),
                    new String[]{UnitTestHelper.randomWord(length),
                            UnitTestHelper.randomWord(length),
                            UnitTestHelper.randomWord(length)},
                    cast
            );
        }
    }
}