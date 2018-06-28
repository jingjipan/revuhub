package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.UserObject;

public interface UserAccess {

    //Search for a user using a loose search
    UserObject getUser(String userName);

    //Search for a user using a strict search
    UserObject getUserSimple(String userName);

    //Add a new user into the storage
    void addNewUser(String userName, UserObject u);
}