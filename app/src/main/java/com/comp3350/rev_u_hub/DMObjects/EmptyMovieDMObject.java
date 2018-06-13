package com.comp3350.rev_u_hub.DMObjects;

import java.io.File;
import java.util.ArrayList;

public class EmptyMovieDMObject extends MovieDMObject {

    public EmptyMovieDMObject() {}

    public boolean isEmpty() {return true;}

    public String getSynopsis() {
        return "N/A";
    }

    public ArrayList<String> getCast() {
        ArrayList<String> cast = new ArrayList<>();
        cast.add("N/A");
        return cast;
    }

    public File getPhoto(int index) {
        return new File("\\some\\path\\to\\a\\default\\image");
    }

    public String getNews() {
        return "N/A";
    }

    public String getTitle() {return "No Movie Found";}
}
