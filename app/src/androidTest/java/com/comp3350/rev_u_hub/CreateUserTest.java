package com.comp3350.rev_u_hub;


import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.presentation_layer.LoginActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateUserTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void createUser() {
        //testing user creation
        onView(withId(R.id.createNavButton)).perform(click());

        onView(withId(R.id.createUsernameField)).perform(typeText("new"));
        onView(withId(R.id.createPasswordField)).perform(typeText("test"));
        onView(withId(R.id.confirmPasswordField)).perform(typeText("test"));

        onView(withId(R.id.createUserButton)).perform(click());
        closeSoftKeyboard();

        //verify that the user logged in
        onView(withId(R.id.currentUsername)).check(matches(withText("Welcome new!")));

        //move back to login screen
        pressBack();
        pressBack();

        //verify that user now exists in db and can log in
        onView(withId(R.id.loginUsernameField)).perform(typeText("new"));
        onView(withId(R.id.loginPasswordField)).perform(typeText("test"));
        onView(withId(R.id.loginButton)).perform(click());
        closeSoftKeyboard();

        onView(withId(R.id.currentUsername)).check(matches(withText("Welcome new!")));
    }

    @After
    public void after() {
        AccountManager manger = Services.getAccountManager();
        try {
            manger.removeUser("new", "test");
        }
        catch (UserDataException e){
            System.out.println(e);
        }
    }

}
