package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;

public interface ReviewManager {

    // Creates and stores a review if its parameters are valid
    ReviewObject createReview(String setReview, String movieName, String userName)
            throws ReviewCreationException;

    // Modifies a stored review's text
    ReviewObject editReview(String reviewName, String movieName, String userName)
            throws ReviewDataException;

    //throws ReviewDataException
    void removeReview(ReviewObject review) throws ReviewDataException;
}