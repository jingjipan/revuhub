package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;

public class AccountManagement implements AccountManager{
    private UserSearch myUserSearch;
    private UserObject currentUser;

    public AccountManagement(UserSearch userSearch) {
        myUserSearch = userSearch;
    }

    //throws UserCreationException
    public UserObject createUser(String userName, String password1, String password2)
            throws UserCreationException {
        UserObject user = getUser(userName,password);

        return null;
    }

    //throws UserDataException
    public void removeUser(String userName, String password) throws UserDataException {
        UserObject user = getUser(userName,password);
    }

    //throws UserDataException
    public UserObject changeUsername(String userName, String password) throws UserDataException {
        UserObject user = getUser(userName,password);

        return null;
    }

    //throws UserDataException
    public UserObject changePassword(String userName, String passwordOld, String passwordNew)
            throws UserDataException {
        UserObject user = getUser(userName,passwordOld);

        return null;
    }

    private UserObject getUser(String userName, String password) throws UserDataException {
        UserObject user = myUserSearch.getUserSimple(userName);

        if (user==null || user.isEmpty())
            throw new UserDataNotFoundException("The selected user does not exist.");

        if (!user.getPassWord().equals(password))
            throw new UserDataNotFoundException("The password is incorrect for the selected user.");
        
        return user;
    }
}