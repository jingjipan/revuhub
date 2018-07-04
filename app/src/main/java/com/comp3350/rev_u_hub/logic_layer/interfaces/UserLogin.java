package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;

public interface UserLogin {

    // If credentials are correct, sets the current user
    UserObject userLogin(String username, String password) throws UserDataException;

    // Returns the current user
    UserObject getUser() throws UserDataNotFoundException;

    // Returns whether a user is currently logged in
    boolean loggedIn();
}