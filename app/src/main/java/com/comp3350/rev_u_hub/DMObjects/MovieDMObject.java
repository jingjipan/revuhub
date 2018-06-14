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
        myMovie = validateMovie(setMovie);
    }

    public MovieDMObject(MovieDMObject other) {
        if (other.isEmpty()) myMovie = null;
        else myMovie = new movie(other.getTitle(),
                other.getSynopsis(),
                other.getPhotoList(),
                other.getCast(),
                other.getNews(),
                other.getReviews());
    }

    public boolean isEmpty() {return myMovie==null;}

    public String getSynopsis() {
        return myMovie.getIntro();
    }

    public String getCast() {
        return myMovie.getCast();
    }

    public File getPhoto() {
        return getPhoto(0);
    }

    public File getPhoto(int index) {
        return new File(myMovie.getPicsUrls()[index]);
    }

    public String[] getPhotoList() { return myMovie.getPicsUrls();}

    public List<String> getNews() {
        return myMovie.getNews();
    }

    public List<String> getReviews() { return myMovie.getReviews();}

    public int setReviews(List<String> setReviews) {
        myMovie.changeReviews(setReviews);
        return myMovie.getReviews().size();
    }

    public int setReviews(String setReview) {
        List<String> reviews = new ArrayList<>();
        reviews.add(setReview);
        myMovie.changeReviews(reviews);
        return myMovie.getReviews().size();
    }

    public int setReviews(ReviewDMObject other) {
        myMovie.changeReviews(other.getReviews());
        return myMovie.getReviews().size();
    }

    public boolean hasReviews() {
        return myMovie.getReviews()!=null && !myMovie.getReviews().isEmpty();
    }

    public String getTitle() {return isEmpty() ? "No Movie Found" : myMovie.getMovieName();}

    public String toString() {return getTitle();}

    public movie getMovie() {return myMovie;}

    public boolean equals(MovieDMObject other) {
        if (other.isEmpty()) return isEmpty();

        return isEmpty()==other.isEmpty() &&
                getTitle().equals(other.getTitle()) &&
                getSynopsis().equals(other.getSynopsis()) &&
                getNews().equals(other.getNews()) &&
                getCast().equals(other.getCast()) &&
                getReviews().equals(other.getReviews()) &&
                Arrays.equals(getPhotoList(),other.getPhotoList());
    }

    movie validateMovie(movie theMovie) {
        return theMovie!=null &&
                theMovie.getMovieName()!=null &&
                !theMovie.getMovieName().equals("") &&
                theMovie.getReviews()!=null &&
                theMovie.getPicsUrls()!=null &&
                theMovie.getCast()!=null &&
                theMovie.getIntro()!=null &&
                theMovie.getNews()!=null ? theMovie : null;
    }
}