package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.DMObjects.ReviewDMObject;
import com.comp3350.rev_u_hub.LogicLayer.LogicInterface;

import java.io.File;
import java.util.ArrayList;

public class SearchHandler implements LogicInterface {
    TypoEngine myTypoEngine;

    public SearchHandler() {
        myTypoEngine = new TypoEngine();
    }

    public String getSynopsis(MovieDMObject movie) {return null;} //temporary

    public ArrayList<String> getCast(MovieDMObject movie) {return null;} //temporary

    public File getPhoto(MovieDMObject movie) {return null;} //temporary

    public String getNews(MovieDMObject movie) {return null;} //temporary

    public ReviewDMObject getReview(MovieDMObject movie) {return null;} //temporary

    public boolean setReview(MovieDMObject movie, ReviewDMObject review) {return false;} //temporary

    public MovieDMObject getMovie(String title) {
        return myTypoEngine.searchDefault(title);
    }
}