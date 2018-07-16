package com.comp3350.rev_u_hub;


import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.comp3350.rev_u_hub.presentation_layer.LoginActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchMovieTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);



    @Test
    public void searchMovie() {
        //testing login
        onView(withId(R.id.loginUsernameField)).perform(typeText("admin"));
        onView(withId(R.id.loginPasswordField)).perform(typeText("123456"));
        onView(withId(R.id.loginButton)).perform(click());
        closeSoftKeyboard();

        //verify the login
        onView(withId(R.id.currentUsername)).check(matches(withText("Welcome admin!")));

        //search for an existing movie
        onView(withId(R.id.movieSearch)).perform(click()).perform(typeText("Deadpool"));
        onView(withId(R.id.movieSearch)).perform(pressImeActionButton());

        //verify that the movie was found
        //onView(withId(R.id.movieTitle)).check(matches(withText("Deadpool")));
    }





}
