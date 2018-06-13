package com.comp3350.rev_u_hub.DMObjects;

import com.comp3350.rev_u_hub.PersistenceLayer.movie;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieDMObject {

    movie myMovie;

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

    public String[] getPhotoList() { return myMovie.getPicsUrls();}

    public String getNews() {
        return "This movie has been released to the public!";
    }

    public List<String> getReviews() { return myMovie.getReviews();}

    public String getTitle() {return myMovie.getMovieName();}

    public String toString() {return getTitle();}

    public boolean equals(MovieDMObject other) {
        return isEmpty()==other.isEmpty() &&
                getTitle().equals(other.getTitle()) &&
                getSynopsis().equals(other.getSynopsis()) &&
                getNews().equals(other.getNews()) &&
                getCast().equals(other.getCast()) &&
                getReviews().equals(other.getReviews()) &&
                Arrays.equals(getPhotoList(),other.getPhotoList());
    }
}