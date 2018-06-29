package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.UserObject;

public interface UserLogin {

    //throws UserDataException
    UserObject userLogin(String username, String password);

    UserObject getUser();

    boolean loggedIn();
}