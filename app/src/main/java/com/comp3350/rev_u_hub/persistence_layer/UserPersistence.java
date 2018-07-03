package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.UserObject;

import java.util.List;

public interface UserPersistence {

    ///Get all of the users
    List<UserObject> getUserSequential();

    ///Get user based on userName
    List<UserObject> searchUser(String userName);

    ///Add a new user into the system
    UserObject addNewUser(UserObject newUser);

    ///Update the password of a specific user
    UserObject updatePassWord(UserObject currentUser);

    ///Delete a specific user
    void deleteUser(UserObject currentUser);
}