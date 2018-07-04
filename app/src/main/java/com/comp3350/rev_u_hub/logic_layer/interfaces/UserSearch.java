package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.UserObject;

public interface UserSearch {

    //Search for a user using a loose search
    UserObject getUser(String userName);

    //Search for a user using a strict search
    UserObject getUserSimple(String userName);
}