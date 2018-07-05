package com.comp3350.rev_u_hub.persistence_layer.stubs;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserAccontPersistenceStub implements UserPersistence{
    private List<UserObject> users;

    public UserAccontPersistenceStub(){
        this.users = new ArrayList<>();

        users.add(new UserObject("admin","123456"));
        users.add(new UserObject("test1","123456"));

        users.add(new UserObject("test2","123456"));
    }
    @Override
    public List<UserObject> getUserSequential() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public List<UserObject> searchUser(String userName) {
        List<UserObject> newUsers = new ArrayList<>();
        UserObject UserObjects;
        int counter;
        for(counter=0;counter< users.size();counter++){
            UserObjects =  users.get(counter);
            if(  UserObjects .getUserName().equals(userName)){
                newUsers.add(users.get(counter));
            }
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

