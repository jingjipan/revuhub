package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.logic_layer.ReviewQuery;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.UserDataException;
import com.comp3350.rev_u_hub.logic_layer.ReviewManagement;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;
import com.comp3350.rev_u_hub_tests.utils.TestUtils;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReviewCreateIT {
    private File tempDB;
    private ReviewPersistence myPersistenceLayer;
    private ReviewManagement myReviewManager;
    private ReviewQuery myReviewQuery;
    private UserLogin userLogin;
    private ReviewObject myReview;
    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.myPersistenceLayer = Services.getReviewPersistence();
        userLogin = Services.getUserLogin();
        myReviewQuery = new ReviewQuery(myPersistenceLayer);
        this.myReviewManager=new ReviewManagement(myPersistenceLayer);
    }

    @Test
    public void testCreateValidReview() {
        System.out.println("Start testing creation of a new valid review.");
        myReview = null;
        try{
            userLogin.userLogin("test3","123456");
        }catch(UserDataException e){
            e.printStackTrace();
        }
        try{
            myReviewManager.createReview("123","Thor","test3");
            myReview=myReviewQuery.getReview("Thor","test3");
        }catch(ReviewCreationException e){
            e.printStackTrace();
        }catch(ReviewDataException e){
            e.printStackTrace();
        }
        assertNotNull("The review shall be in the database.",myReview);
        assertTrue("The new message should be in the database.",myReviewQuery.reviewExists("Thor","test3"));
        assertTrue("The newly added review should be '123'.",myReview.getReview().equals("123"));

        System.out.println("Finished testing creation of a new valid review.");
    }

    @Test
    public void testCreateExistingReview() {
        boolean message=false;
        System.out.println("Start testing creation of a existing review.");
        myReview = null;
        try{
            userLogin.userLogin("admin","123456");
        }catch(UserDataException e){
            e.printStackTrace();
        }

        try{
            myReviewManager.createReview("123","Thor","admin");
        }catch(ReviewCreationException e){
            message=true;
        }
        try {
            myReview=myReviewQuery.getReview("Thor","admin");
        }catch (ReviewDataException e){
            e.printStackTrace();
        }
        assertNotNull("The review shall be in the database.",myReview);
        assertTrue("Create duplicate review successfully.",message);
        assertTrue("The review shall still exist.",myReviewQuery.reviewExists("Thor","admin"));
        assertTrue("Review shall not be changed.",!myReview.getReview().equals("123"));

        System.out.println("Finished testing creation of a existing review.");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }
}
