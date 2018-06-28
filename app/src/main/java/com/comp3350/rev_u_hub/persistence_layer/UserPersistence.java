package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.UserObject;

public interface UserPersistence {

    ///Initialize default data
    void addStaticInfo();

    ///Search for specific user in the map
    UserObject searchUser(String userName);

    ///Add a new user into the storage
    void addNewUser(String userName, UserObject u);

    ///Update the stored copy of a user
    void updateUser(String userName, UserObject u);
}