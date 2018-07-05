package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.data_objects.SearchableObject;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;

import java.util.ArrayList;
import java.util.List;

public class UserSearchEngine extends SearchEngine implements UserSearch {
    private UserPersistence myPersistenceLayer;
    private List<UserObject> lastRetrieval;
    private List<String> lastRetrievalStrings;

    public UserSearchEngine(UserPersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
    }

    public UserObject getUserSimple(String userName) {
        retrieveFromPersistence();
        return (UserObject) getObjectSimple(userName);
    }

    // Uses Damerau–Levenshtein_distance 1 permutations of a title to search
    // https://en.wikipedia.org/wiki/Damerau–Levenshtein_distance
    public UserObject getUser(String userName) {
        retrieveFromPersistence();
        return (UserObject) getObject(userName);
    }

    // Required to allow SearchEngine methods to search the persistence layer
    protected SearchableObject fetchPersistent(String searchText) {
        UserObject user = (UserObject) defaultObject();
        for(int i=0; i<lastRetrievalStrings.size(); i++) {
            if (lastRetrievalStrings.get(i).equals(searchText))
                user = lastRetrieval.get(i);
        }
        return user;
    }

    // Required to tell SearchEngine what to return if the search fails
    protected SearchableObject defaultObject() {
        return new UserObject();
    }

    private void retrieveFromPersistence() {
        lastRetrievalStrings =  new ArrayList<>();
        lastRetrieval = myPersistenceLayer.getUserSequential();
        for(int i=0; i<lastRetrieval.size(); i++) {
            lastRetrievalStrings.add(lastRetrieval.get(i).getUserName());
        }
    }
}