package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationDuplicateException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;
import com.comp3350.rev_u_hub_tests.UserTestHelper;
import com.comp3350.rev_u_hub_tests.persistence.UserAccontPersistenceStub;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AccountManagerUnitTest {
    private UserPersistence persistenceLayer = new UserAccontPersistenceStub();
    private UserSearch userSearch = new UserSearchEngine(persistenceLayer);
    private AccountManager accountManager = new AccountManagement(userSearch,persistenceLayer);
    private static final String newPassWord = "NEW_PASS";
    private static final String newUserName = "NEW_NAME";

    @Test
    public void testUserEditings() {
        UserObject[] testUser = UserTestHelper.getUserTestArray();
        for (int i = 0; i < UserTestHelper.TEST_DESCRIPTORS.length; i++) {
            testUserEditing(testUser[i], UserTestHelper.TEST_DESCRIPTORS[i]);
        }
    }
    private void testUserEditing(UserObject testUser, String description) {
        String testUserName = testUser.getUserName();
        String testUserPassword = testUser.getPassWord();
        boolean runModifyDelete = true;

        System.out.println("\nTesting AccountManagement methods' functionality with " +
                description + ".");

        System.out.println("    User Name: \"" + testUser + "\"");

        assertNotNull(testUser);
        assertNotNull(testUserName);

        System.out.println("    Testing user creation...");
        try {
            accountManager.createUser(testUserName,testUserPassword,testUserPassword);
        } catch (UserCreationException e) {
            printException(e);
            runModifyDelete = false;
        }
        assertTrue(testUser.equals(userSearch.getUserSimple(testUserName)));

        if (runModifyDelete) {
            System.out.println("    Testing password modification...");
            testUser.changePassWord(newPassWord);
            try {
                accountManager.changePassword(testUserName, testUserPassword, newPassWord);
            } catch (UserDataException e) {
                printException(e);
            }
            assertTrue(testUser.equals(userSearch.getUserSimple(testUserName)));

            System.out.println("    Testing username modification...");
            testUser = new UserObject(newUserName, newPassWord);
            try {
                accountManager.changeUsername(testUserName, newUserName, newPassWord);
            } catch (UserCreationDuplicateException e) {
                printException(e);
            } catch (UserDataException e) {
                printException(e);
            }
            assertTrue(testUser.equals(userSearch.getUserSimple(newUserName)));

            System.out.println("    Testing user removal...");
            try {
                accountManager.removeUser(newUserName, newPassWord);
            } catch (UserDataException e) {
                printException(e);
            }
            assertTrue(!testUser.equals(userSearch.getUserSimple(newUserName)));
        }

        System.out.println("Completed testing AccountManagement methods' functionality with " +
                description + ".");
    }

    private void printException(Exception e) {
        System.out.println("        Exception: "+e.getMessage());
    }
}