package com.comp3350.rev_u_hub.DMObjects;

import java.util.ArrayList;

public class EmptyMovieDMObject extends MovieDMObject {

    private ArrayList<String> noList;

    public EmptyMovieDMObject() {
        noList = new ArrayList<>();
        noList.add("N/A");
    }

    public boolean hasReviews() {return false;}
}