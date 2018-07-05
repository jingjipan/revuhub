package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationFailedException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordMismatchException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationUsernameConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;

public class AccountManagement implements AccountManager{
    private UserSearch myUserSearch;
    private UserPersistence myPersistenceLayer;

    public AccountManagement(UserSearch setUserSearch, UserPersistence setPersistenceLayer) {
        myUserSearch = setUserSearch;
        myPersistenceLayer = setPersistenceLayer;
    }

    // Creates and stores a user if given valid parameters
    public UserObject createUser(String userName, String password1, String password2)
            throws UserCreationException {
        UserObject user;

        if (!password1.equals(password2)) throw new UserCreationPasswordMismatchException(
                "The passwords given do not match.");

        checkUsernameConstraint(userName);
        checkPasswordConstraint(password1);

        if (!myUserSearch.getUserSimple(userName).isEmpty())
            throw new UserCreationDuplicateException("The user "+userName+" already exists.");

        user = new UserObject(userName,password1);
        myPersistenceLayer.addNewUser(user);

        if (myUserSearch.getUserSimple(userName).isEmpty())
            throw new UserCreationFailedException("The user "+userName+" could not be created.");

        return user;
    }

    // Removes a user from storage
    public void removeUser(String userName, String password) throws UserDataException {
        UserObject user = getUserObject(userName,password);

        myPersistenceLayer.deleteUser(user);
    }

    // Changes a stored user's username
    public UserObject changeUsername(String userNameOld, String userNameNew, String password)
            throws UserDataException, UserCreationDuplicateException {
        UserObject userOld = getUserObject(userNameOld,password);
        UserObject userNew;

        myPersistenceLayer.deleteUser(userOld);
        if (!myUserSearch.getUserSimple(userNameOld).isEmpty())
            throw new UserCreationDuplicateException(
                    "The previous user could not be overwritten.");
        userNew = new UserObject(userNameNew,password);
        myPersistenceLayer.addNewUser(userNew);
        return userNew;
    }

    // Changes a stored user's password
    public UserObject changePassword(String userName, String passwordOld, String passwordNew)
            throws UserDataException {
        UserObject user = getUserObject(userName,passwordOld);

        user.changePassWord(passwordNew);
        myPersistenceLayer.updatePassWord(user);
        return user;
    }

    private UserObject getUserObject(String userName, String password) throws UserDataException {
        UserObject user = myUserSearch.getUserSimple(userName);

        if (user==null || user.isEmpty())
            throw new UserDataNotFoundException("The selected user does not exist.");

        if (!user.getPassWord().equals(password))
            throw new UserDataNotFoundException("The password is incorrect for the selected user.");
        
        return user;
    }

    private void checkUsernameConstraint(String userName)
            throws UserCreationUsernameConstraintException {
        if (userName.equals("")) throw new UserCreationUsernameConstraintException(
                "Username must not be empty");
    }

    private void checkPasswordConstraint(String password)
            throws UserCreationPasswordConstraintException {
        if (password.equals("")) throw new UserCreationPasswordConstraintException(
                "Password must not be empty");
    }
}