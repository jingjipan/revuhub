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

public class ReviewModifyIT {
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
    public void testModifyValidReview() {
        System.out.println("Start testing modification of a valid review.");
        myReview = null;
        try{
            userLogin.userLogin("admin","123456");
        }catch(UserDataException e){
            e.printStackTrace();
        }
        try{
            myReviewManager.editReview("123","Thor","admin");
        }catch(ReviewDataException e){
            e.printStackTrace();
        }
        try {
            myReview=myReviewQuery.getReview("Thor","admin");
        }catch (ReviewDataException e){
            e.printStackTrace();
        }
        assertNotNull("The review shall be in the database.",myReview);
        assertTrue("The review shall be edited.",myReview.getReview().equals("123"));

        System.out.println("Finished testing modification of a valid review.");
    }

    @Test
    public void testModifyInvalidReview() {
        System.out.println("Start testing modification of an invalid review.");
        myReview = null;
        boolean message = false;
        try{
            userLogin.userLogin("admin","123456");
        }catch(UserDataException e){
            e.printStackTrace();
        }
        try{
            myReviewManager.editReview("","Thor","admin");
        }catch(ReviewDataException e){
            message=true;
        }
        try {
            myReview=myReviewQuery.getReview("Thor","admin");
        }catch (ReviewDataException e){
            e.printStackTrace();
        }
        assertNotNull("The review shall be in the database.",myReview);
        assertTrue("Testing invalid review, this message shall appear.",message);
        assertTrue("The review shall not be edited.",myReview.getReview().equals(""));

        System.out.println("Finished testing modification of an invalid review.");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }
}
