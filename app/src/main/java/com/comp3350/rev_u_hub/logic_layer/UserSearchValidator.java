package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationUsernameConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;

public class UserSearchValidator {
    private UserSearch myUserSearch;

    public UserSearchValidator(UserSearch setSearch) {
        myUserSearch = setSearch;
    }

    public UserObject getUserWithPassword(String userName, String password)
            throws UserDataException {
        UserObject user = myUserSearch.getUserSimple(userName);

        if (user==null || user.isEmpty())
            throw new UserDataNotFoundException("The selected user does not exist.");

        if (!user.getPassWord().equals(password))
            throw new UserDataNotFoundException("The password is incorrect for the selected user.");

        return user;
    }

    public void checkUsernameConstraint(String userName)
            throws UserCreationUsernameConstraintException {
        if (userName.equals("")) throw new UserCreationUsernameConstraintException(
                "Username must not be empty");
    }

    public void checkPasswordConstraint(String password)
            throws UserCreationPasswordConstraintException {
        if (password.equals("")) throw new UserCreationPasswordConstraintException(
                "Password must not be empty");
    }

    public UserObject getUserSimple(String userName) throws UserDataNotFoundException {
        UserObject user = myUserSearch.getUserSimple(userName);
        if (user.isEmpty())
            throw new UserDataNotFoundException("The user "+userName+
                    " does not exist.");
        return user;
    }

    public UserObject getUser(String userName) throws UserDataNotFoundException {
        UserObject user = myUserSearch.getUserSimple(userName);

        if (user.isEmpty()) {
            user = myUserSearch.getUser(userName);
            if (user.isEmpty())
                throw new UserDataNotFoundException("No user with that username was found.");
            else throw new UserDataNotFoundException("No user with that username was found." +
                    "  Did you mean " + user.getUserName() + "?");
        }

        return user;
    }

    public boolean checkUserExists(String userName) throws UserDataNotFoundException {
        UserObject user = myUserSearch.getUserSimple(userName);
        if (user.isEmpty())
            throw new UserDataNotFoundException("No user with that username was found.");
        return true;
    }

    public boolean checkUserAbsent(String userName) throws UserDataException {
        UserObject user = myUserSearch.getUserSimple(userName);
        if (!user.isEmpty())
            throw new UserDataException("A user with that username was found.");
        return true;
    }
}