package com.comp3350.rev_u_hub.DMObjects;

import com.comp3350.rev_u_hub.PersistenceLayer.movie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EmptyMovieDMObject extends MovieDMObject {

    private ArrayList<String> noList;

    public EmptyMovieDMObject() {
        noList = new ArrayList<>();
        noList.add("N/A");
    }

    public boolean isEmpty() {return true;}

    public String getSynopsis() {
        return "N/A";
    }

    public String getCast() {return "N/A";}

    public File getPhoto(int index) {
        return new File("\\some\\path\\to\\a\\default\\image");
    }

    public ArrayList<String> getNews() {
        return noList;
    }

    public ArrayList<String> getReviews() {return noList;}

    public int setReviews(List<String> setReviews) {return noList.size();}

    public int setReviews(String setReview) {return noList.size();}

    public int setReviews(ReviewDMObject other) {return noList.size();}

    public boolean hasReviews() {return false;}

    public String getTitle() {return "No Movie Found";}

    public movie getMovie() {return null;}
}