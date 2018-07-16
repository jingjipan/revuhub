package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;


import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;
import com.comp3350.rev_u_hub_tests.utils.TestUtils;
import com.comp3350.rev_u_hub.Application.Services;

import org.hsqldb.rights.User;
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
        this.myUserSearch = new UserSearchEngine(myPersistenceLayer);
        this.accountManager = new AccountManagement(myUserSearch, myPersistenceLayer);
    }

    @Test
    public void testModifyValidUserName() {
        System.out.println("Start testing valid modification of a valid user name.");
        UserObject userObject = null;
        try {
            accountManager.changeUsername("admin", "test123", "123456");
        } catch (UserDataException e) {
            e.printStackTrace();
        } catch (UserCreationDuplicateException e) {
            e.printStackTrace();
        }
        userObject = myUserSearch.getUserSimple("admin");
        assertTrue("user should not exist in database", userObject.getUserName().equals("") && userObject.getPassWord().equals(""));
        userObject = myUserSearch.getUserSimple("test123");
        assertTrue("test123".equals(userObject.getUserName()));
        assertTrue("123456".equals(userObject.getPassWord()));
        System.out.println("Finished testing valid modification of a valid user name.");
    }

    @Test
    public void testModifyValidPassword() {
        System.out.println("Start testing valid modification of a valid user password.");
        UserObject userObject = null;
        try {
            accountManager.changePassword("admin", "123456", "654321");
        } catch (UserDataException e) {
            e.printStackTrace();
        } catch (UserCreationPasswordConstraintException e) {
            e.printStackTrace();
        }
        userObject = myUserSearch.getUserSimple("admin");
        assertTrue("admin".equals(userObject.getUserName()));
        assertTrue("654321".equals(userObject.getPassWord()));
        System.out.println("Finished testing valid modification of a valid user password.");
    }

    @Test
    public void testModifyInvalidUser() {
        System.out.println("Start testing modification of invalid user account.");
        boolean cond=false;
        try {
            accountManager.changeUsername("amd", "test1", "123456");
        } catch(UserDataNotFoundException e){
           cond = true;
        } catch (UserDataException e) {
            e.printStackTrace();
        } catch (UserCreationDuplicateException e) {
            e.printStackTrace();
        }
        assertTrue("The name is invalid.",cond);
        try {
            accountManager.changePassword("admin", "123", "65");
        }catch(UserDataNotFoundException e){
            cond = true;
        }catch (UserDataException e) {
            e.printStackTrace();
        } catch (UserCreationPasswordConstraintException e) {
            e.printStackTrace();
        }
        assertTrue("The password is incorrect for the selected user.",cond);
        System.out.println("Finished testing modification of invalid user account.");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }
}
