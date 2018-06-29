package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.UserObject;

public interface AccountManager {

    //throws UserCreationException
    UserObject createUser(String userName, String password1, String password2);

    //throws UserDataException
    void removeUser(String userName, String password);

    //throws UserDataException
    UserObject changeUsername(String userName, String password);

    //throws UserDataException
    UserObject changePassword(String userName, String passwordOld, String passwordNew);
}