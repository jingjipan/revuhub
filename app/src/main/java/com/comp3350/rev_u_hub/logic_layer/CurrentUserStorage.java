package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataWrongPasswordException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;

public class CurrentUserStorage implements UserLogin {

    private UserSearchValidator myUserSearchValidator;
    private UserObject currentUser;

    public CurrentUserStorage(UserSearch userSearch) {
        myUserSearchValidator = new UserSearchValidator(userSearch);
    }

    // If credentials are correct, sets the current user
    public UserObject userLogin(String username, String password) throws UserDataException {
        UserObject user = myUserSearchValidator.getUser(username);

        if (!user.getPassWord().equals(password))
              throw new UserDataWrongPasswordException(
                      "The password entered is incorrect for this user.");

        currentUser = user;
        return currentUser;
    }

    // Returns the current user
    public UserObject getUser() throws UserDataNotFoundException {
        if (currentUser==null || currentUser.isEmpty())
            throw new UserDataNotFoundException("No user is currently logged in.");
        return currentUser;
    }

    // Returns whether a user is currently logged in
    public boolean loggedIn() {
        return currentUser!=null && !currentUser.isEmpty();
    }
}