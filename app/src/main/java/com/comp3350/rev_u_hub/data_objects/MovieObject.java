package com.comp3350.rev_u_hub.data_objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieObject implements Serializable, SearchableObject {
    private String title;
    private String synopsis, cast;
    private List<String> reviews;

    public MovieObject() {
        title = "";
        synopsis = "";
        cast = "";
        reviews = new ArrayList<>();
    }

    public MovieObject(String movieName, String synopsis, String cast, List<String> reviews) {
        this.title = movieName;
        this.synopsis = synopsis;
        this.reviews = reviews;
        this.cast = cast;
    }

//    public MovieObject(movie setMovie) {
//        myMovie = validateMovie(setMovie);
//    }

    public MovieObject(MovieObject other) {
        if (other != null && !other.isEmpty()) {
            this.title = other.getTitle();
            this.synopsis = other.getSynopsis();
            this.reviews = other.getReviews();
            this.cast = other.getCast();
        } else {
            title = "";
            synopsis = "";
            cast = "";
            reviews = new ArrayList<>();
        }
    }

    public static boolean validateMovie(MovieObject theMovie) {
        return theMovie != null &&
                theMovie.getTitle()!= null &&
                !theMovie.getTitle().equals("") &&
                theMovie.getReviews()!=null &&
                theMovie.getCast()!=null &&
                theMovie.getSynopsis()!=null;
    }

    public boolean isEmpty() {
        return title.equals("") && synopsis.equals("") && cast.equals("") && reviews.isEmpty();
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getCast() {
        return cast;
    }

    public List<String> getReviews() { return reviews;}

    public void setReviews(String setReview) {
        reviews.add(setReview);
    }

    public void setReviews(ReviewObject other) {
        reviews.addAll(other.getReviews());
    }

    public void setReviews(List<String> setReviews) {
        this.reviews = setReviews;
    }

    public boolean hasReviews() {
        return reviews != null && !reviews.isEmpty();
    }

    public String getTitle() {return !title.equals("") ? title : "No Movie Found";}

    public String toString() {return getTitle();}

    public boolean equals(MovieObject other) {
        return this.getTitle().equals(other.getTitle()) &&
                this.getSynopsis().equals(other.getSynopsis()) &&
                this.getCast().equals(other.getCast()) &&
                this.getReviews().equals(other.getReviews());
    }

    public MovieObject getMovie() {
        return this;
    }
}