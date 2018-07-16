package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;


import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;
import com.comp3350.rev_u_hub_tests.utils.TestUtils;
import com.comp3350.rev_u_hub.Application.Services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class AccountCreateIT {
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
    public void testCreateUserObject() {
        UserObject userObject=null;
        try {
            userObject = accountManager.createUser("Tom", "123456", "123456");
        }catch (UserCreationException e) {
            e.printStackTrace();
            assertNotNull("user should not null", userObject);
        }
        assertNotNull("User should be stored in database",userObject=myUserSearch.getUserSimple("Tom"));
        assertTrue("Tom".equals(userObject.getUserName()));
        assertTrue("123456".equals(userObject.getPassWord()));

        System.out.println("Finished test Create Account");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}