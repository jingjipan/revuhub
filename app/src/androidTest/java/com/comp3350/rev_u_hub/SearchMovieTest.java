package com.comp3350.rev_u_hub;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.SearchView;

import com.comp3350.rev_u_hub.presentation_layer.HomeActivity;
import com.comp3350.rev_u_hub.presentation_layer.LoginActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;


public class SearchMovieTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    private SearchView mSearchView;

    public SearchMovieTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testCreateRoutine() {
        solo.waitForActivity("LoginActivity");
        solo.clickOnEditText(0);
        String username = "admin";
        solo.enterText(0, username);

        solo.clickOnEditText(1);
        String password = "123456";
        solo.enterText(1, password);
        solo.clickOnView(solo.getView("loginButton"));

        solo.waitForActivity("HomeActivity");
        String movieName = "Deadpool";
        View search = solo.getView(R.id.movieSearch);
        ((SearchView) search).setQuery(movieName, true);

        solo.waitForActivity("MovieOverviewActivity");

        // Check for moviename
        assertTrue(solo.searchText(movieName));

        // Check for synopsis
        String synopsis = "A fast-talking mercenary with a morbid sense of humor is subjected to a rogue experiment that leaves him with accelerated healing powers and a quest for revenge.";
        assertTrue(solo.searchText(synopsis));

        String cast = "Ryan Reynolds, Karan Soni, Ed Skrein, Michael Benyaer";
        assertTrue(solo.searchText(cast));
    }
}