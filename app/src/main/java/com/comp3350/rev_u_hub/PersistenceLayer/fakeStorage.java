package com.comp3350.rev_u_hub.PersistenceLayer;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class fakeStorage implements PersistenceInterface {
    HashMap<String, movie> movieMap;

    public fakeStorage() {
        movieMap = new HashMap<String, movie>();
        this.addStaticInfo();
    }

    ///Add default movies objects into the HashMap
    public void addStaticInfo() {
        List<String> reviews1 = new ArrayList<String>();
        List<String> news1 = new ArrayList<String>();
        news1.add("No news");
        reviews1.add("Nice Movie.");
        reviews1.add("Poor Movie.");
        reviews1.add("Movie is fine.");
        String[] pic1 = new String[]{"aa.jpg", "bb.jpg"};
        String cast1 = "Robert Downey Jr.";
        movie m1 = new movie("The Avengers", "A Marvel movie", pic1, cast1, news1, reviews1);

        List<String> reviews2 = new ArrayList<String>();
        List<String> news2 = new ArrayList<String>();
        news2.add("No news");
        reviews2.add("Nice Movie.");
        reviews2.add("Poor Movie.");
        reviews2.add("Movie is fine.");
        String cast2 = "Ryan Reynolds";

        movie m2 = new movie("DeadPool", "A Marvel movie", null, cast2, news2, reviews2);

        List<String> reviews3 = new ArrayList<String>();
        List<String> news3 = new ArrayList<String>();
        news3.add("No news");
        reviews3.add("Nice Movie.");
        reviews3.add("Poor Movie.");
        reviews3.add("Movie is fine.");
        String cast3 = "Chris Hemsworth";

        movie m3 = new movie("Thor", "A Marvel movie", null, cast3, news3, reviews2);

        movieMap.put("The Avengers", m1);
        movieMap.put("DeadPool", m1);
        movieMap.put("Thor", m1);
    }

    ///Search for specific movie in the map
    public movie searchMovie(String movieName) {
        return movieMap.get(movieName);  //If the movie does not exist in the storage, a null would be returned.
    }

    ///Add a new movie into the storage
    public void addNewMovie(String movieName, movie m) {
        movieMap.put(movieName, m);
    }

}
