package com.comp3350.rev_u_hub.DMObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieDMObject implements Serializable {
    String title;
    String synopsis, cast;
    List<String> reviews;

    MovieDMObject() {
        title = "";
        synopsis = "";
        cast = "";
        reviews = new ArrayList<String>();
    }

    public MovieDMObject(String movieName, String synopsis, String cast, List<String> reviews) {
        this.title = movieName;
        this.synopsis = synopsis;
        this.reviews = reviews;
        this.cast = cast;
    }

//    public MovieDMObject(movie setMovie) {
//        myMovie = validateMovie(setMovie);
//    }

    public MovieDMObject(MovieDMObject other) {
        if (other != null && !other.isEmpty()) {
            this.title = other.getTitle();
            this.synopsis = other.getSynopsis();
            this.reviews = other.getReviews();
            this.cast = other.getCast();
        } else {
            title = "";
            synopsis = "";
            cast = "";
            reviews = new ArrayList<String>();
        }
    }

    public boolean isEmpty() {
        return title == "" && synopsis == "" && cast == "" && reviews.isEmpty();
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getCast() {
        return cast;
    }

    public List<String> getReviews() { return reviews;}


    public void setReviews(List<String> setReviews) {
        this.reviews = setReviews;
    }

    public void setReviews(String setReview) {
        reviews.add(setReview);
    }

    public void setReviews(ReviewDMObject other) {
        reviews.addAll(other.getReviews());
    }

    public boolean hasReviews() {
        return reviews != null && !reviews.isEmpty();
    }

    public String getTitle() {return title != "" ? title : "No Movie Found";}

    public String toString() {return getTitle();}

    public boolean equals(MovieDMObject other) {
        return this.getTitle().equals(other.getTitle()) &&
                this.getSynopsis().equals(other.getSynopsis()) &&
                this.getCast().equals(other.getCast()) &&
                this.getReviews().equals(other.getReviews());
    }

    public static boolean validateMovie(MovieDMObject theMovie) {
        return theMovie != null &&
                theMovie.getTitle()!= null &&
                !theMovie.getTitle().equals("") &&
                theMovie.getReviews()!=null &&
                theMovie.getCast()!=null &&
                theMovie.getSynopsis()!=null;
    }

    public MovieDMObject getMovie() {
        return this;
    }
}