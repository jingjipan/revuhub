package com.comp3350.rev_u_hub;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.SearchView;

import com.comp3350.rev_u_hub.presentation_layer.HomeActivity;
import com.comp3350.rev_u_hub.presentation_layer.LoginActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;


public class ChangePasswordTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    private SearchView mSearchView;

    public ChangePasswordTest() {
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
        String usernameSearch = "admin";
        View search = solo.getView(R.id.userSearch);
        ((SearchView) search).setQuery(usernameSearch, true);

        solo.waitForActivity("ProfileActivity");

        // Check for username
        assertTrue(solo.searchText(usernameSearch));

        solo.clickOnEditText(0);
        String oldPassword = "123456";
        solo.enterText(0, oldPassword);
        solo.clickOnEditText(0);
        String newPassword = "newPass";
        solo.enterText(0, newPassword);
        solo.clickOnView(solo.getView("loginButton"));

        solo.goBack();
        solo.waitForActivity("HomeActivity");
        solo.goBack();
        solo.waitForActivity("LoginActivity");

        solo.clickOnEditText(0);
        solo.enterText(0, username);

        solo.clickOnEditText(1);
        password = "newPass";
        solo.enterText(1, password);
        solo.clickOnView(solo.getView("loginButton"));

        solo.waitForActivity("HomeActivity");
        assertTrue(solo.searchText(usernameSearch));
    }
}