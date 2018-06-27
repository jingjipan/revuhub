package com.comp3350.rev_u_hub.PersistenceLayer.stubs;

import com.comp3350.rev_u_hub.DMObjects.MovieObject;
import com.comp3350.rev_u_hub.PersistenceLayer.MoviePersistence;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MoviePersistenceStub implements MoviePersistence {
    private TreeMap<String, MovieObject> movieMap;

    public MoviePersistenceStub() {
        movieMap = new TreeMap<String, MovieObject>(String.CASE_INSENSITIVE_ORDER);
        this.addStaticInfo();
    }

    @Override
    public void addStaticInfo() {
        List<String> reviews1 = new ArrayList<String>();
        List<String> news1 = new ArrayList<String>();
        news1.add("No news");
        reviews1.add("Nice Movie.");
        reviews1.add("Poor Movie.");
        reviews1.add("Movie is fine.");
        String cast1 = "Robert Downey Jr.";
        String synopsis1 = "Earth\'s mightiest heroes must come together and learn to fight" +
                "as a team if they are going to stop the mischievous Loki and his alien army" +
                "from enslaving humanity.";

        MovieObject m1 = new MovieObject("The Avengers", synopsis1, cast1, reviews1);

        List<String> reviews2 = new ArrayList<String>();
        List<String> news2 = new ArrayList<String>();
        news2.add("No news");
        reviews2.add("Nice Movie.");
        reviews2.add("Poor Movie.");
        reviews2.add("Movie is fine.");
        String cast2 = "Ryan Reynolds";
        String synopsis2 = "A fast-talking mercenary with a morbid sense of humor is subjected to a" +
                "rogue experiment that leaves him with accelerated healing powers and a" +
                "quest for revenge.";

        MovieObject m2 = new MovieObject("DeadPool", synopsis2, cast2, reviews2);

        List<String> reviews3 = new ArrayList<String>();
        List<String> news3 = new ArrayList<String>();
        reviews3.add("Nice Movie.");
        reviews3.add("Poor Movie.");
        reviews3.add("Movie is fine.");
        String cast3 = "Chris Hemsworth";
        String synopsis3 = "The powerful, but arrogant god Thor, is cast out of Asgard to live amongst" +
                "humans in Midgard (Earth), where he soon becomes one of their finest defenders.";

        MovieObject m3 = new MovieObject("Thor", synopsis3, cast3, reviews2);

        movieMap.put("The Avengers", m1);
        movieMap.put("DeadPool", m2);
        movieMap.put("Thor", m3);
    }

    @Override
    public MovieObject searchMovie(String movieName) {
        return movieMap.get(movieName);  //If the movie does not exist in the storage, a null would be returned.
    }

    @Override
    public void addNewMovie(String movieName, MovieObject m) {
        movieMap.put(movieName, m);
    }
}
