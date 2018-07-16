package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
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


public class AccountAccessIT {
    private UserSearchEngine userSearchEngine;
    private File tempDB;
    private UserPersistence myPersistenceLayer;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.myPersistenceLayer = Services.getUserPersistence();
        this.userSearchEngine = new UserSearchEngine(myPersistenceLayer);
    }

    @Test
    public void testValidUser() {
        System.out.println("Start testing access of a valid user.");
        UserObject userObject;
        userObject = userSearchEngine.getUserSimple("admin");
        assertNotNull("admin user should in the database",userObject);
        assertTrue("admin".equals(userObject.getUserName()));
        assertTrue("123456".equals(userObject.getPassWord()));

        System.out.println("Finished testing access of a valid user.");
    }

    @Test
    public void testInvalidUser() {
        System.out.println("Start testing access of an invalid user.");
        UserObject userObject;
        userObject = userSearchEngine.getUserSimple("test123");
        assertTrue("".equals(userObject.getUserName()));
        assertTrue("".equals(userObject.getPassWord()));

        System.out.println("Finished testing access of an invalid user.");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }

}
