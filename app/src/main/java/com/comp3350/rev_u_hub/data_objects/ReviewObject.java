package com.comp3350.rev_u_hub.data_objects;

import android.support.annotation.NonNull;

public class ReviewObject implements SearchableObject, Comparable {

    private String review;
    private String movieName;
    private String userName;

    public ReviewObject() {
        this.review = "";
        this.movieName = "";
        this.userName = "";
    }

    public ReviewObject(String setReview,String movieName, String userName) {
        this.review = setReview;
        this.movieName = movieName;
        this.userName = userName;
    }

    public String getReview() {
        return review;
    }

    public String getUserName() {
        return userName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setReview(String setReview) {
        review = setReview;
    }

    public boolean isEmpty() {
        return review.equals("");
    }

    public boolean equals(ReviewObject other) {
        return getReview().equals(other.getReview());
    }

    public String toString() {
        return getReview();
    }

    public int compareTo(@NonNull Object o) {
        int output;
        double difference;

        if (o instanceof ReviewObject) {
            difference = review.length() - ((ReviewObject) o).getReview().length();
            output = difference > 0 ? 1 : difference < 0 ? -1 : 0;
        } else {
            output = 0;
        }

        return output;
    }
}