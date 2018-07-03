package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;

public interface AccountManager {

    // Creates and stores a user if given valid parameters
    UserObject createUser(String userName, String password1, String password2)
            throws UserCreationException;

    // Removes a user from storage
    void removeUser(String userName, String password) throws UserDataException;

    // Changes a stored user's username
    UserObject changeUsername(String userName, String password) throws UserDataException,
            UserCreationDuplicateException;

    // Changes a stored user's password
    UserObject changePassword(String userName, String passwordOld, String passwordNew)
            throws UserDataException;
}