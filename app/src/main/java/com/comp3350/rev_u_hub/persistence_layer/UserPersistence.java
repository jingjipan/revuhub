package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.UserObject;

public interface UserPersistence {

    ///Search for specific user in the map
    UserObject searchUser(String userName);

    ///Add a new user into the storage
    UserObject addNewUser(UserObject u);

    ///Update the stored copy of a user
    UserObject updateUser(UserObject u);

    ///Delete the stored copy of a user
    void removeUser(UserObject u);
}