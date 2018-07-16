package com.comp3350.rev_u_hub_tests.logic_layer_tests;


import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;
import com.comp3350.rev_u_hub_tests.utils.TestUtils;
import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.data_objects.UserObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;



public class AccountRemoveIT {
    private AccountManager accountManager;
    private File tempDB;
    private UserPersistence myPersistenceLayer;
    private UserSearch myUserSearch;




    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.myPersistenceLayer = Services.getUserPersistence();
        this.myUserSearch=new UserSearchEngine(myPersistenceLayer);
        this.accountManager= new AccountManagement(myUserSearch,myPersistenceLayer);
    }

    @Test
    public void testRemoveUserObject() {
        UserObject userObject=null;
        try {
            userObject = accountManager.createUser("Tom", "123456", "123456");
        }catch (UserCreationException e) {
            e.printStackTrace();
            assertNotNull("user should not null", userObject);
        }
        try {
            accountManager.removeUser("Tom","123456");
        }catch (UserDataException e) {
            e.printStackTrace();
        }
        assertTrue("User name should not be stored in database",myUserSearch.getUserSimple("Tom").getUserName().equals(""));
        assertTrue("User password should not be stored in database",myUserSearch.getUserSimple("Tom").getPassWord().equals(""));

        System.out.println("Finished test Remove Account");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}
