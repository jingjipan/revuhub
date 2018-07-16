package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;


import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
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
        System.out.println("Start testing Remove Account");
        UserObject userObject=null;
        try {
            accountManager.removeUser("admin","123456");
        }catch (UserDataException e) {
            e.printStackTrace();
        }
        assertTrue("User name should not be stored in database",myUserSearch.getUserSimple("Tom").getUserName().equals(""));
        assertTrue("User password should not be stored in database",myUserSearch.getUserSimple("Tom").getPassWord().equals(""));

        System.out.println("Finished test Remove Account");
    }
    @Test
    public void testRemoveInvalidUserObject() {
        System.out.println("Start testing Remove invalid Account");
        boolean cond = false;
        try {
            accountManager.removeUser("adm","123456");
        }catch (UserDataNotFoundException e){
            cond = true;
        }
        catch (UserDataException e) {
            e.printStackTrace();
        }
        assertTrue("The selected user does not exit",cond);
       System.out.println("Finished test Remove invalid Account");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }
}
