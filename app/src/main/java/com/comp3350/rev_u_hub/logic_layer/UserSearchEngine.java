package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.SearchableObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;

public class UserSearchEngine extends SearchEngine implements UserAccess {
    private UserPersistence myPersistenceLayer;

    public UserSearchEngine() {
        myPersistenceLayer = null;  //change later
    }

    public UserSearchEngine(UserPersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    public void addNewUser(String userName, UserObject u) {
        myPersistenceLayer.addNewUser(userName, u);
    }

    public UserObject getUserSimple(String userName) {
        return (UserObject) getObjectSimple(userName);
    }

    // Uses Damerau–Levenshtein_distance 1 permutations of a name to search
    // https://en.wikipedia.org/wiki/Damerau–Levenshtein_distance
    public UserObject getUser(String userName) {
        return (UserObject) getObject(userName);
    }

    // Required to allow SearchEngine methods to search the persistence layer
    protected SearchableObject fetchPersistent(String searchText) {
        return myPersistenceLayer.searchUser(searchText);
    }

    // Required to tell SearchEngine what to return if the search fails
    protected SearchableObject defaultObject() {
        return new UserObject("","");
    }
}