package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.ReviewObject;

public interface ReviewPersistence {

    ///Initialize default data
    void addStaticInfo();

    ///Search for specific review in the map
    ReviewObject searchReview(String userName, String movieName);

    ///Add a new review into the storage
    void addNewReview(ReviewObject r);

    ///Update the stored copy of a review
    void updateReview(ReviewObject r);
}