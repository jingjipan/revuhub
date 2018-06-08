package com.comp3350.rev_u_hub;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class fakeStorage {
    HashMap<String,movie> map;
    public fakeStorage(){
        map = new HashMap<String,movie>();
        this.addStaticInfo();
    }

    ///Add default movies objects into the HashMap
    public void addStaticInfo(){
        List<String> reviews1 = new ArrayList<String>();
        reviews1.add("Nice Movie.");
        reviews1.add("Poor Movie.");
        reviews1.add("Movie is fine.");
        movie m1 = new movie("The Avengers", "A Marvel movie",null,reviews1);

        List<String> reviews2 = new ArrayList<String>();
        reviews2.add("Nice Movie.");
        reviews2.add("Poor Movie.");
        reviews2.add("Movie is fine.");
        movie m2 = new movie("DeadPool", "A Marvel movie",null,reviews2);

        List<String> reviews3 = new ArrayList<String>();
        reviews3.add("Nice Movie.");
        reviews3.add("Poor Movie.");
        reviews3.add("Movie is fine.");
        movie m3 = new movie("Thor", "A Marvel movie",null,reviews2);

        map.put("The Avengers",m1);
        map.put("DeadPool",m1);
        map.put("Thor",m1);
    }

    ///Search for specific movie in the map
    public movie searchMovie(String movieName){
        return map.get(movieName);  //If the movie does not exist in the storage, a null would be returned.
    }

    ///Add a new movie into the storage
    public void addNewMovie(String movieName, movie m){
        map.put(movieName,m);
    }

}
