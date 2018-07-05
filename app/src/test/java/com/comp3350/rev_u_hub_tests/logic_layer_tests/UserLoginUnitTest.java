package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.CurrentUserStorage;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;
import com.comp3350.rev_u_hub_tests.UserTestHelper;
import com.comp3350.rev_u_hub_tests.persistence.UserAccontPersistenceStub;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserLoginUnitTest {
    private UserPersistence persistenceLayer = new UserAccontPersistenceStub();
    private UserSearch userSearch = new UserSearchEngine(persistenceLayer);
    private UserLogin userLogin = new CurrentUserStorage(userSearch);

    @Test
    public void testUserLogins() {
        UserObject[] testUser = UserTestHelper.getUserTestArray();
        for (int i = 0; i < UserTestHelper.TEST_DESCRIPTORS.length; i++) {
            testUserLogin(testUser[i], UserTestHelper.TEST_DESCRIPTORS[i]);
        }
    }
    private void testUserLogin(UserObject testUser, String description) {
        String testUserName = testUser.getUserName();
        String testUserPassword = testUser.getPassWord();
        boolean loginSuccess = true;

        System.out.println("\nTesting CurrentUserStorage login with " +
                description + ".");

        persistenceLayer.addNewUser(new UserObject(testUserName,testUserPassword));

        System.out.println("    User Name: \"" + testUser + "\"");

        assertNotNull(testUser);
        assertNotNull(testUserName);

        System.out.println("    Testing user login...");
        try {
            userLogin.userLogin(testUserName,testUserPassword);
        } catch (UserDataException e) {
            printException(e);
            loginSuccess = false;
        }
        assertTrue(testUser.equals(userSearch.getUserSimple(testUserName)));

        if (loginSuccess) {
            System.out.println("    Testing successful login...");
            try {
                assertTrue(testUser.equals(userLogin.getUser()));
            } catch (UserDataNotFoundException e) {
                printException(e);
            }
        } else {
            System.out.println("    Testing unsuccessful login...");
            try {
                assertFalse(testUser.equals(userLogin.getUser()));
            } catch (UserDataNotFoundException e) {
                printException(e);
            }
        }

        System.out.println("Completed testing CurrentUserStorage login with " +
                description + ".");
    }

    private void printException(Exception e) {
        System.out.println("        Exception: "+e.getMessage());
    }
}