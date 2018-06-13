package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.EmptyMovieDMObject;
import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;

public abstract class LogicConstants {
    public static final String lowercaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final String uppercaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String numbers = "0123456789";
    public static final String symbols = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static final String allChars = "0123456789" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    public static MovieDMObject noMovie = new EmptyMovieDMObject();
}