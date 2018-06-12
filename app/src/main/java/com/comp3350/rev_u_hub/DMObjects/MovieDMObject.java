package com.comp3350.rev_u_hub.DMObjects;

import com.comp3350.rev_u_hub.PersistenceLayer.movie;

import java.io.File;
import java.util.ArrayList;

public class MovieDMObject {
    private movie myMovie;

    MovieDMObject() {}

    public MovieDMObject(movie setMovie) {
        myMovie = setMovie;
    }

    public boolean isEmpty() {return myMovie!=null;}

    public String getSynopsis() {
        return myMovie.getIntro();
    }

    public ArrayList<String> getCast() {
        ArrayList<String> cast = new ArrayList<>();
        cast.add("A");
        cast.add("B");
        cast.add("C");
        return cast;
    }

    public File getPhoto() {
        return getPhoto(0);
    }

    public File getPhoto(int index) {
        return new File(myMovie.getPicsUrls()[index]);
    }

    public String getNews() {
        return "This movie has been released to the public!";
    }
}