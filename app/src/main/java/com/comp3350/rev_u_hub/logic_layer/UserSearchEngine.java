package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.SearchableObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;

import java.util.List;

public class UserSearchEngine extends SearchEngine implements UserSearch {
    private UserPersistence myPersistenceLayer;

    public UserSearchEngine(UserPersistence setPersistenceLayer) {
        myPersistenceLayer = setPersistenceLayer;
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
        UserObject user;
        List<UserObject> list = myPersistenceLayer.searchUser(searchText);
        if (list.isEmpty()) user = (UserObject) defaultObject();
        else user = list.get(0);
        return user;
    }

    // Required to tell SearchEngine what to return if the search fails
    protected SearchableObject defaultObject() {
        return new UserObject("","");
    }
}