package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;

public interface AccountManager {

    //throws UserCreationException
    UserObject createUser(String userName, String password1, String password2) throws UserCreationException;

    //throws UserDataException
    void removeUser(String userName, String password) throws UserDataException;

    //throws UserDataException
    UserObject changeUsername(String userName, String password) throws UserDataException;

    //throws UserDataException
    UserObject changePassword(String userName, String passwordOld, String passwordNew) throws UserDataException;
}