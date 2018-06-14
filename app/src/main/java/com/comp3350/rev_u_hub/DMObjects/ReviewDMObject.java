package com.comp3350.rev_u_hub.DMObjects;

import java.util.ArrayList;
import java.util.List;

public class ReviewDMObject {

    private List<String> myReviews;

    public ReviewDMObject() {
        myReviews = new ArrayList<>();
    }

    public ReviewDMObject(String setReview) {
        myReviews = new ArrayList<>();
        myReviews.add(setReview);
    }

    public ReviewDMObject(List<String> setReviews) {
        myReviews = new ArrayList<>(setReviews);
    }

    public ReviewDMObject(ReviewDMObject other) {
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

    public int setReviews(ReviewDMObject other) {
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

    public int addReviews(ReviewDMObject other) {
        myReviews.addAll(other.getReviews());
        return myReviews.size();
    }

    public boolean equals(ReviewDMObject other) {
        return getReviews().equals(other.getReviews());
    }

    public String toString() {
        return getReviews().toString();
    }
}