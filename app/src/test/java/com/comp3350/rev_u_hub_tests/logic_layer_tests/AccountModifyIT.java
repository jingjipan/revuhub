package com.comp3350.rev_u_hub_tests.logic_layer_tests;


import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class AccountModifyIT {
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
    public void testModifyUserObject() {
        UserObject userObject=null;
        try {
            accountManager.changeUsername("test3", "test123", "123456");
        }catch (UserDataException e) {
            e.printStackTrace();
        }catch(UserCreationDuplicateException e){
            e.printStackTrace();
        }
        userObject = myUserSearch.getUserSimple("test3");
        assertTrue("user should not exist in database",userObject.getUserName().equals("")&&userObject.getPassWord().equals("")) ;
        assertNotNull("User should be stored in database",userObject=myUserSearch.getUserSimple("test123"));
        assertTrue("test123".equals(userObject.getUserName()));
        assertTrue("123456".equals(userObject.getPassWord()));

        try {
            accountManager.changePassword("test123", "123456", "654321");
        }catch (UserDataException e) {
            e.printStackTrace();
        }
        assertTrue("test123".equals((userObject=myUserSearch.getUserSimple("test123")).getUserName()));
        assertTrue("654321".equals(userObject.getPassWord()));
        System.out.println("Finished test Account Modify");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}
