package com.comp3350.rev_u_hub.data_objects;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class MovieObject implements Serializable, SearchableObject, Comparable {
    private String title, synopsis, cast;
    private int count;
    private double rating;
    private String pic;
    public MovieObject() {
        title = "";
        synopsis = "";
        cast = "";
    }

    public MovieObject(String movieName, String synopsis, String cast,String pic,int count, double rating) {
        this.title = movieName;
        this.synopsis = synopsis;
        this.cast = cast;
        this.count=count;
        this.rating=rating;
        this.pic=pic;
    }

    public MovieObject(MovieObject other) {
        if (other != null && !other.isEmpty()) {
            this.title = other.getTitle();
            this.synopsis = other.getSynopsis();
            this.cast = other.getCast();
            this.rating = other.getRating();
            this.count = other.getCount();
        } else {
            title = "";
            synopsis = "";
            cast = "";
            rating = 0;
            count = 0;
        }
    }

    public static boolean validateMovie(MovieObject theMovie) {
        return theMovie != null &&
                theMovie.getTitle()!= null &&
                !theMovie.getTitle().equals("") &&
                 theMovie.getCast()!=null &&
                theMovie.getSynopsis()!=null;
    }

    public void updateRating(double newRating){
        double temp=rating*count+newRating;
        count++;
        rating=temp/count;

    }

    public int getCount(){
        return count;
    }

    public double getRating(){
        return rating;
    }

    public boolean isEmpty() {
        return title.equals("") && synopsis.equals("") && cast.equals("");
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getCast() {
        return cast;
    }

    public String getTitle() {return !title.equals("") ? title : "No Movie Found";}

    public String getPic(){
        return pic;
    }

    public String toString() {return getTitle();}

    public boolean equals(MovieObject other) {
        return this.getTitle().equals(other.getTitle()) &&
                this.getSynopsis().equals(other.getSynopsis()) &&
                this.getCast().equals(other.getCast());
    }

    public MovieObject getMovie() {
        return this;
    }

    public int compareTo(@NonNull Object o) {
        int output;
        double difference;

        if (o instanceof MovieObject) {
            difference = getRating() - ((MovieObject) o).getRating();
            output = difference > 0 ? -1 : difference < 0 ? 1 : 0;
        } else {
            output = 0;
        }

        return output;
    }
}