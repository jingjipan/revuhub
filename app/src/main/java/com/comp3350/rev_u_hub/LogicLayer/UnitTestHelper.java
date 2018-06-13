package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.DMObjects.RandomMovieDMObject;

import java.util.Random;

public abstract class UnitTestHelper {
    private static Random random = new Random();
    private static final int DEFAULT_LENGTH = 64;

    public static int randomInteger() {
        return randomInteger(1,DEFAULT_LENGTH+1);
    }

    public static int randomInteger(int max) {  // 0 inclusive to max exclusive
        return random.nextInt(max);
    }

    public static int randomInteger(int min, int max) { // min inclusive to max exclusive
        return min + randomInteger(max - min);
    }

    // alphanumeric characters and symbols from http://www.asciitable.com/
    public static String randomString() {
        return randomString(randomInteger(1,65));
    }

    public static String randomWord() { // letters only, starting with a capital letter
        return randomWord(randomInteger(1,65));
    }

    // alphanumeric characters and symbols from http://www.asciitable.com/
    public static String randomString(int length) {
        String randString = "";
        for (int i=0; i<length; i++) {
            randString += (char) randomInteger(32,127);
        }
        return randString;
    }

    public static String randomWord(int length) { // letters only, starting with a capital letter
        String randString = "";
        if (length>0)  randString += (char) randomInteger(65,91);
        for (int i=1; i<length; i++) {
            randString += (char) randomInteger(97,123);
        }
        return randString;
    }

    public static MovieDMObject randomMovie() {
        return new RandomMovieDMObject();
    }

    // allChars = all characters, not just words
    public static MovieDMObject randomMovie(boolean allChars) {
        return new RandomMovieDMObject(allChars);
    }

    public static MovieDMObject randomMovie(int length) {
        return new RandomMovieDMObject(length);
    }

    // allChars = all characters, not just words
    public static MovieDMObject randomMovie(boolean allChars, int length) {
        return new RandomMovieDMObject(allChars, length);
    }

    public static String randomPermutation(String theString, String validChars) {
        int permutation = randomInteger(4);
        String altered = theString;

        switch (permutation) {
            case 0:  altered = randomDeletion(altered);
                break;
            case 1:  altered = randomTransposition(altered);
                break;
            case 2:  altered = randomInsertion(altered, validChars);
                break;
            case 3:  altered = randomSubstitution(altered, validChars);
                break;
            default: altered = randomSubstitution(altered, validChars);
                break;
        }
        return altered;
    }

    public static String randomDeletion(String theString) {
        int position;
        if (theString.length()<1) return theString;

        position = randomInteger(0,theString.length());

        return theString.substring(0,position) +
                theString.substring(position,theString.length());
    }

    public static String randomTransposition(String theString) {
        int position;
        if (theString.length()<2) return theString;

        position = randomInteger(0,theString.length()-1);

        return theString.substring(0,position) +
                theString.substring(position+1,position+2) +
                theString.substring(position,position+1) +
                theString.substring(position+2,theString.length());
    }

    public static String randomInsertion(String theString, String validChars) {
        int position;
        if (theString.length()<1)
            return "" + validChars.charAt(randomInteger(0,validChars.length()));

        position = randomInteger(0,theString.length()+1);

        return theString.substring(0, position) +
                validChars.charAt(randomInteger(0,validChars.length())) +
                theString.substring(position, theString.length());
    }

    public static String randomSubstitution(String theString, String validChars) {
        int position;
        if (theString.length()<1) return theString;

        position = randomInteger(0,theString.length());

        return theString.substring(0, position) +
                validChars.charAt(randomInteger(0,validChars.length())) +
                theString.substring(position+1, theString.length());
    }
}