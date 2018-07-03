package com.comp3350.rev_u_hub.logic_layer.interfaces;

import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewCreationException;
import com.comp3350.rev_u_hub.logic_layer.exceptions.ReviewDataException;

public interface ReviewManager {

    //throws ReviewCreationException
    ReviewObject createReview(String setReview, String movieName, String userName, int rating) throws ReviewCreationException;

    //throws ReviewDataException
    ReviewObject editReview(String reviewName, String movieName, String userName, int rating) throws ReviewDataException;

    //throws ReviewDataException
    void removeReview(String reviewName) throws ReviewDataException;
}