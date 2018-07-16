package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.logic_layer.ReviewQuery;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNotFoundException;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;
import com.comp3350.rev_u_hub_tests.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ReviewAccessIT {
    private ReviewPersistence reviewPersistence;
    private File tempDB;
    private ReviewQuery reviewQuery;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.reviewPersistence = Services.getReviewPersistence();
        //this.newMovie = (MovieObject) Services.getMovieLists();
        this.reviewQuery=new ReviewQuery(reviewPersistence);
    }

    @Test
    public void testAccessValidReview() {
        System.out.println("Start testing access valid review");
        ReviewObject reviewObject = null;
        try{
            reviewObject = reviewQuery.getReview("Thor","admin");
        }catch(ReviewDataNotFoundException e){
            e.printStackTrace();
        }
        assertNotNull("review should be in lists",reviewObject);
        assertTrue("admin".equals(reviewObject.getUserName()));

        System.out.println("Finished testing access valid review");
    }

    @Test
    public void testAccessInvalidReview() {
        System.out.println("Start testing access invalid review");
        boolean message = false;
        try{
            reviewQuery.getReview("Thor","test3");
        }catch(ReviewDataNotFoundException e){
           message=true;
        }
        assertTrue("The review should not in the database.",message);
        System.out.println("Finished testing access invalid review");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
        Services.clean();
    }
}
