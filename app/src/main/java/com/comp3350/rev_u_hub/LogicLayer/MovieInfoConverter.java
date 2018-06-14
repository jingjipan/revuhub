package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;

import java.util.ArrayList;
import java.io.File;

public class MovieInfoConverter implements MovieInfoGetter {

    public MovieInfoConverter() {}

    public String getSynopsis(MovieDMObject movie) {
        return InfoFormatConverter.convertToString(movie.getSynopsis());
    }

    public ArrayList<String> getCast(MovieDMObject movie) {
        return (ArrayList<String>) InfoFormatConverter.convertToStringList(movie.getCast());
    }

    public File getPhoto(MovieDMObject movie) { //returns file path to movie
        return InfoFormatConverter.convertToFile(movie.getPhoto());
    }

    public String getNews(MovieDMObject movie) {
        return InfoFormatConverter.convertToString(movie.getNews());
    }
}