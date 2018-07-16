package com.comp3350.rev_u_hub.logic_layer;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationFailedException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordMismatchException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;

public class AccountManagement implements AccountManager{
    private UserSearchValidator myUserSearchValidator;
    private UserPersistence myPersistenceLayer;

    public AccountManagement(UserSearch setUserSearch, UserPersistence setPersistenceLayer) {
        myUserSearchValidator = new UserSearchValidator(setUserSearch);
        myPersistenceLayer = setPersistenceLayer;
    }

    // Creates and stores a user if given valid parameters
    public UserObject createUser(String userName, String password1, String password2)
            throws UserCreationException {
        UserObject user;

        if (!password1.equals(password2)) throw new UserCreationPasswordMismatchException(
                "The passwords given do not match.");

        myUserSearchValidator.checkUsernameConstraint(userName);
        myUserSearchValidator.checkPasswordConstraint(password1);

        try {
            myUserSearchValidator.checkUserAbsent(userName);
        } catch (UserDataException e) {
            throw new UserCreationDuplicateException("The user " + userName + " already exists.");
        }

        user = new UserObject(userName,password1);
        myPersistenceLayer.addNewUser(user);

        try {
            myUserSearchValidator.checkUserExists(userName);
        } catch (UserDataNotFoundException e) {
            throw new UserCreationFailedException("The user " + userName + " could not be created.");
        }

        return user;
    }

    // Removes a user from storage
    public void removeUser(String userName, String password) throws UserDataException {
        UserObject user = myUserSearchValidator.getUserWithPassword(userName,password);

        myPersistenceLayer.deleteUser(user);
    }

    // Changes a stored user's username
    public UserObject changeUsername(String userNameOld, String userNameNew, String password)
            throws UserDataException, UserCreationDuplicateException {
        UserObject userOld = myUserSearchValidator.getUserWithPassword(userNameOld,password);
        UserObject userNew;

        myPersistenceLayer.deleteUser(userOld);
        try {
            myUserSearchValidator.checkUserAbsent(userNameOld);
        } catch (UserDataException e) {
            throw new UserCreationDuplicateException(
                    "The previous user could not be overwritten.");
        }
        userNew = new UserObject(userNameNew,password);
        myPersistenceLayer.addNewUser(userNew);
        return userNew;
    }

    // Changes a stored user's password
    public UserObject changePassword(String userName, String passwordOld, String passwordNew)
            throws UserDataException, UserCreationPasswordConstraintException {
        UserObject user = myUserSearchValidator.getUserWithPassword(userName,passwordOld);

        myUserSearchValidator.checkPasswordConstraint(passwordNew);
        user.changePassWord(passwordNew);
        myPersistenceLayer.updatePassWord(user);
        return user;
    }
}