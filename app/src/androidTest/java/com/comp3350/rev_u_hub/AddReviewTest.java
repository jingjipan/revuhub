package com.comp3350.rev_u_hub;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.SearchView;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.presentation_layer.HomeActivity;
import com.comp3350.rev_u_hub.presentation_layer.LoginActivity;
import com.robotium.solo.Solo;
import java.lang.Thread;
import junit.framework.Assert;


public class AddReviewTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    private SearchView mSearchView;

    public AddReviewTest() {
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
        solo.clickOnView(solo.getView("createNavButton"));
        solo.waitForActivity("CreateUserActivity");

        solo.clickOnEditText(0);
        String username = "new";
        solo.enterText(0, username);

        solo.clickOnEditText(1);
        String password = "pass";
        solo.enterText(1, password);

        solo.clickOnEditText(2);
        String newPassword = "pass";
        solo.enterText(2, newPassword);
        solo.clickOnView(solo.getView("createUserButton"));

        solo.waitForActivity("HomeActivity");
        String movieName = "Deadpool";
        View search = solo.getView(R.id.movieSearch);
        ((SearchView) search).setQuery(movieName, true);

        solo.waitForActivity("MovieOverviewActivity");
        solo.clickOnEditText(0);
        String newReview = "newReview";
        solo.enterText(0, newReview);

        solo.clickOnView(solo.getView("submitButton"));
        try {
            Thread.sleep(6000);
        }
        catch(Exception e) {
            System.out.println(e);
        }


        assertTrue(solo.searchText(newReview));

        AccountManager manger = Services.getAccountManager();
        try {
            manger.removeUser("new", "pass");
        }
        catch (UserDataException e){
            System.out.println(e);
        }


    }
}