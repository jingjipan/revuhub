package com.comp3350.rev_u_hub.DMObjects;

import java.util.ArrayList;
import java.util.List;

public class ReviewObject {

    private List<String> myReviews;

    public ReviewObject() {
        myReviews = new ArrayList<>();
    }

    public ReviewObject(String setReview) {
        myReviews = new ArrayList<>();
        myReviews.add(setReview);
    }

    public ReviewObject(List<String> setReviews) {
        myReviews = new ArrayList<>(setReviews);
    }

    public ReviewObject(ReviewObject other) {
        myReviews = new ArrayList<>(other.getReviews());
    }

    public List<String> getReviews() {
        return myReviews;
    }

    public int setReviews(List<String> setReviews) {
        myReviews = new ArrayList<>(setReviews);
        return myReviews.size();
    }

    public int setReviews(String setReview) {
        myReviews = new ArrayList<>();
        myReviews.add(setReview);
        return myReviews.size();
    }

    public int setReviews(ReviewObject other) {
        myReviews = new ArrayList<>(other.getReviews());
        return myReviews.size();
    }

    public int addReviews(List<String> setReviews) {
        myReviews.addAll(setReviews);
        return myReviews.size();
    }

    public int addReviews(String setReview) {
        myReviews.add(setReview);
        return myReviews.size();
    }

    public int addReviews(ReviewObject other) {
        myReviews.addAll(other.getReviews());
        return myReviews.size();
    }

    public boolean equals(ReviewObject other) {
        return getReviews().equals(other.getReviews());
    }

    public String toString() {
        return getReviews().toString();
    }
}