package com.comp3350.rev_u_hub.persistence_layer;

import com.comp3350.rev_u_hub.data_objects.ReviewObject;

public interface ReviewPersistence {

    ///Search for specific review in the map
    ReviewObject searchReview(String userName, String movieName);

    ///Add a new review into the storage
    ReviewObject addNewReview(ReviewObject r);

    ///Update the stored copy of a review
    ReviewObject updateReview(ReviewObject r);

    ///Delete the stored copy of a review
    void removeReview(ReviewObject r);
}