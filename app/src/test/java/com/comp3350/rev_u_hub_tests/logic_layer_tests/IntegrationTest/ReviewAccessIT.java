package com.comp3350.rev_u_hub_tests.logic_layer_tests.IntegrationTest;

import com.comp3350.rev_u_hub.Application.Services;
import com.comp3350.rev_u_hub.data_objects.MovieObject;
import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.logic_layer.ReviewQuery;
import com.comp3350.rev_u_hub.logic_layer.exceptions.MovieDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataNotFoundException;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewSearch;
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
    private MovieObject newMovie;
    private ReviewQuery reviewQuery;

    @Before
    public void setUp() throws IOException {
        this.tempDB = TestUtils.copyDB();
        this.reviewPersistence = Services.getReviewPersistence();
        //this.newMovie = (MovieObject) Services.getMovieLists();
        this.reviewQuery=new ReviewQuery(reviewPersistence);
    }

    @Test
    public void testReviewObject() {
        ReviewObject reviewObject = null;
        try{
            reviewObject = reviewQuery.getReview("Thor","admin");
        }catch(ReviewDataNotFoundException e){
            e.printStackTrace();
        }
        assertNotNull("review should be in lists",reviewObject);
        assertTrue("admin".equals(reviewObject.getUserName()));

        System.out.println("Finished test AccessReview");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();
    }
}
