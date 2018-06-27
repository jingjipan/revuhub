package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieObject;

import java.util.ArrayList;

public class MovieInfoConverter implements MovieInfoGetter {

    public MovieInfoConverter() {}

    public String getSynopsis(MovieObject movie) {
        return InfoFormatConverter.convertToString(movie.getSynopsis());
    }

    public ArrayList<String> getCast(MovieObject movie) {
        return (ArrayList<String>) InfoFormatConverter.convertToStringList(movie.getCast());
    }
}