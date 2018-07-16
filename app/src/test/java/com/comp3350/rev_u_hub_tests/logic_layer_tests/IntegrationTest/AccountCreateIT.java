package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;


import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
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

import static org.junit.Assert.assertNull;
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
        System.out.println("Start testing creation of a valid user account.");
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

        System.out.println("Finished testing creation of a valid user account.");
    }

    @Test
    public void testCreateDuplicateUserObject(){
        System.out.println("Start testing creation of a duplicate user account.");
        UserObject userObject = null;
        boolean cond = false;
        try {
            userObject = accountManager.createUser("Tom", "123456", "123456");

        }catch (UserCreationException e) {
            e.printStackTrace();
        }
        assertNotNull("User shall be stored in the database.",userObject);
        try {
            userObject = accountManager.createUser("Tom", "123456", "123456");
        }catch (UserCreationException e) {
            cond = true;
        }
        assertTrue("The duplicated name has been created",cond);
        System.out.println("Finished testing creation of a duplicate user account.");

    }
    @Test
    public void testPassWordMismatch(){
        System.out.println("Start testing creation of user account with password mismatched.");
        UserObject userObject = null;
        boolean cond = false;
        try {
            userObject = accountManager.createUser("MemberJ", "123", "123456");
        }catch (UserCreationException e) {
           cond = true;
        }
        assertNull("The account shall not be created.",userObject);
        assertTrue("Tne password mismatched",cond);
        System.out.println("Finished testing creation of user account with password mismatched.");

    }
    @Test
    public void testInvaildPassWord(){
        boolean message=false;
        System.out.println("Start testing creation of user account with password invalid.");
        try {
            accountManager.createUser("MemberJ", "", "");
        }catch (UserCreationException e) {
            message=true;
        }
        assertTrue("User should be stored in database",myUserSearch.getUserSimple("MemberJ").getUserName().equals(""));
        assertTrue("User account with invalid password should not be created.",message);
        System.out.println("Finished testing creation of user account with password invalid.");
    }

    @Test
    public void testInvaildUserName(){
        boolean message=false;
        System.out.println("Start testing creation of user account with user name invalid.");
        try {
            accountManager.createUser("", "123456", "123456");
        }catch (UserCreationException e) {
            message=true;
        }
        assertTrue("User should be stored in database",myUserSearch.getUserSimple("").getUserName().equals(""));
        assertTrue("User account with invalid password should not be created.",message);
        System.out.println("Finished testing creation of user account with user name invalid.");

    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }

}
