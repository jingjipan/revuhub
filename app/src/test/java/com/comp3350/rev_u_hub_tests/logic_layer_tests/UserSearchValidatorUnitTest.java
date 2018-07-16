package com.comp3350.rev_u_hub_tests.logic_layer_tests;

import com.comp3350.rev_u_hub.data_objects.UserObject;
import com.comp3350.rev_u_hub.logic_layer.UserSearchValidator;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationPasswordConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserCreationUsernameConstraintException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserSearchValidatorUnitTest {
    private UserSearch userSearch = mock(UserSearch.class);
    private UserSearchValidator userSearchValidator = spy(new UserSearchValidator(userSearch));

    @Before
    public void setUp() {
        when(userSearch.getUserSimple("Elizabeth")).thenReturn(
                new UserObject("Elizabeth","")
        );
        when(userSearch.getUserSimple("Susan")).thenReturn(
                new UserObject("Susan","")
        );
        when(userSearch.getUser("Susan")).thenReturn(
                new UserObject("Susan","")
        );
        when(userSearch.getUserSimple("Roberto")).thenReturn(
                new UserObject());
        when(userSearch.getUserSimple("Ivan")).thenReturn(
                new UserObject());
        when(userSearch.getUser("Ivan")).thenReturn(
                new UserObject("Ivana",""));
        when(userSearch.getUserSimple("Jennifer")).thenReturn(
                new UserObject("Jennifer",""));
        when(userSearch.getUserSimple("R2D2")).thenReturn(
                new UserObject());
        when(userSearch.getUserSimple("Justin")).thenReturn(
                new UserObject("Justin","Trudeau12345"));
    }

    @Test
    public void testSearches() {
        System.out.println("\nTesting UserSearchValidator exceptions.");
        testFound("Elizabeth", true);
        testFound("Susan", false);
        testAbsent("Roberto", true);
        testAbsent("Ivan", false);
        testSearch("Jennifer","R2D2");
        testPasswords("Justin","Trudeau12345","Pierre","hunter2");
        testConstraints("Bob User-man","i<3mum");
        System.out.println("Completed testing UserSearchValidator exceptions.");
    }

    private void testFound(String userName, boolean simple) {
        boolean thrown = false;

        System.out.println("    Searching should pass for \"" + userName + "\"");

        assertNotNull(userName);

        try {
            if (simple) userSearchValidator.getUserSimple(userName).getUserName();
            else userSearchValidator.getUser(userName).getUserName();
        } catch (UserDataNotFoundException e) {
            thrown = true;
        }

        assertEquals("Search should not throw an exception", false, thrown);

        try {
            if (simple) verify(userSearchValidator,
                    times(1)).getUserSimple(userName);
            else verify(userSearchValidator,
                    times(1)).getUser(userName);
        } catch (UserDataNotFoundException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }
    }

    private void testConstraints(String username, String password) {
        int exceptions = 0;
        System.out.println("    Testing username and password constraints.");

        try {
            userSearchValidator.checkUsernameConstraint(username);
            userSearchValidator.checkPasswordConstraint(password);
        } catch (UserCreationUsernameConstraintException |
                UserCreationPasswordConstraintException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }

        try {
            userSearchValidator.checkUsernameConstraint("");
        } catch (UserCreationUsernameConstraintException e) {
            exceptions++;
        }

        try {
            userSearchValidator.checkPasswordConstraint("");
        } catch (UserCreationPasswordConstraintException e) {
            exceptions++;
        }

        assertEquals("Two exceptions should have been thrown.",2,exceptions);
    }

    private void testPasswords(String goodUser, String goodPass, String badUser, String badPass) {
        int exceptions = 0;
        System.out.println("    Testing username and password validation.");

        exceptions += countUserPasswordException(goodUser, goodPass);
        assertEquals("The username and password should have succeeded.",
                0,exceptions);
        exceptions += countUserPasswordException(goodUser, badPass);
        exceptions += countUserPasswordException(badUser, goodPass);
        exceptions += countUserPasswordException(badUser, badPass);
        assertEquals("Three exceptions should have been thrown.",3,exceptions);

        verify(userSearch,times(2)).getUserSimple(goodUser);
        verify(userSearch,times(2)).getUserSimple(badUser);
    }

    private int countUserPasswordException(String username, String password) {
        int thrown = 0;
        try {
            userSearchValidator.getUserWithPassword(username,password);
        } catch (UserDataException e) {
            thrown++;
        }
        return thrown;
    }

    private void testAbsent(String userName, boolean simple) {
        boolean thrown = false;

        System.out.println("    Searching should fail for \"" + userName + "\"");

        assertNotNull(userName);

        try {
            if (simple) userSearchValidator.getUserSimple(userName).getUserName();
            else userSearchValidator.getUser(userName).getUserName();
        } catch (UserDataNotFoundException e) {
            thrown = true;
        }

        assertEquals("Search should throw an exception", true, thrown);

        try {
            if (simple) verify(userSearchValidator,
                    times(1)).getUserSimple(userName);
            else verify(userSearchValidator,
                    times(1)).getUser(userName);
        } catch (UserDataNotFoundException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }
    }

    private void testSearch(String found, String absent) {
        int exceptions = 0;
        System.out.println("    User \"" + found + "\" is in the mock database");
        System.out.println("    User \"" + absent + "\" is not in the mock database");

        try {
            assertTrue("Search should not throw an exception.", userSearchValidator.checkUserExists(found));
            assertTrue("Search should not throw an exception.", userSearchValidator.checkUserAbsent(absent));
        } catch (UserDataException e) {
            printException(e);
            assertTrue("Invalid state due to caught exception.",false);
        }

        try {
            assertTrue("Search should throw an exception.", userSearchValidator.checkUserAbsent(found));
        } catch (UserDataException e) {
            exceptions++;
        }

        try {
            assertTrue("Search should throw an exception.", userSearchValidator.checkUserExists(absent));
        } catch (UserDataException e) {
            exceptions++;
        }

        assertEquals("Search should throw two exceptions", 2, exceptions);

        verify(userSearch, times(2)).getUserSimple(found);
        verify(userSearch, times(2)).getUserSimple(absent);
    }

    private void printException(Exception e) {
        System.out.println("        Exception: "+e.getMessage());
    }
}