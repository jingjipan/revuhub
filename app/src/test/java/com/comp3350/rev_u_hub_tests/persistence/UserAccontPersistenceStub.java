package com.comp3350.rev_u_hub_tests.persistence;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class UserAccontPersistenceStub implements UserPersistence{
    private List<UserObject> users;

    public  UserAccontPersistenceStub(){
        this.users = new ArrayList<>();

        users.add(new UserObject("Tom","123456"));
        users.add(new UserObject("Jack","123456"));
        users.add(new UserObject("Roy","123456"));
    }
    @Override
    public List<UserObject> getUserSequential() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public List<UserObject> searchUser(String userName) {
        List<UserObject> newUsers = new ArrayList<>();
        int index;

        index = users.indexOf("userName");
        if (index >= 0)
        {
            newUsers.add(users.get(index));
        }
        return newUsers;
    }

    @Override
    public UserObject addNewUser(UserObject newUser){
        // don't bother checking for duplicates
        users.add(newUser);
        return newUser;
    }

    @Override
    public UserObject updatePassWord(UserObject currentUser) {
        int index;

        index = users.indexOf(currentUser);
        if (index >= 0)
        {
            users.set(index, currentUser);
        }
        return currentUser;
    }

    @Override
    public void deleteUser(UserObject currentUser){
        int index;

        index = users.indexOf(currentUser);
        if (index >= 0)
        {
            users.remove(index);
        }
    }


}

