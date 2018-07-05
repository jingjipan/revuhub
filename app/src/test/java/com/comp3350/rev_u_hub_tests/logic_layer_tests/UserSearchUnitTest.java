package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;
import com.comp3350.rev_u_hub_tests.UserTestHelper;
import com.comp3350.rev_u_hub_tests.persistence.UserAccontPersistenceStub;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserSearchUnitTest {
    private UserPersistence persistenceLayer = new UserAccontPersistenceStub();
    private static final String allChars = "0123456789" +
            "abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    private UserSearch userSearch = new UserSearchEngine(persistenceLayer);

    @Test
    public void testSearches() {
        UserObject[] testUser = UserTestHelper.getUserTestArray();
        for (int i = 0; i < UserTestHelper.TEST_DESCRIPTORS.length; i++) {
            testSearch(testUser[i], UserTestHelper.TEST_DESCRIPTORS[i]);
        }
    }
    private void testSearch(UserObject testUser, String description) {
        String testUserName = testUser.getUserName();
        String testUserPassword = testUser.getPassWord();

        System.out.println("\nTesting UserInMemorySearchEngine getUser functionality with " +
                description + ".");

        persistenceLayer.addNewUser(new UserObject(testUserName,testUserPassword));

        System.out.println("    User Name: \"" + testUser + "\"");

        assertNotNull(testUser);
        assertNotNull(testUserName);

        assertTrue(testUser.equals(userSearch.getUserSimple(testUserName)));

        assertTrue(testUser.equals(userSearch.getUser(
                UserTestHelper.randomDeletion(testUserName))));
        assertTrue(testUser.equals(userSearch.getUser(
                UserTestHelper.randomTransposition(testUserName))));
        assertTrue(testUser.equals(userSearch.getUser(
                UserTestHelper.randomInsertion(testUserName, allChars))));
        assertTrue(testUser.equals(userSearch.getUser(
                UserTestHelper.randomSubstitution(testUserName, allChars))));

        System.out.println("Completed testing UserSearchEngine getUser functionality with " +
                description + ".");
    }
}
