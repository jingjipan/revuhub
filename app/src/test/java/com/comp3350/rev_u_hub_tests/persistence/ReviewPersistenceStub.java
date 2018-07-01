package com.comp3350.rev_u_hub_tests.persistence;

import com.comp3350.rev_u_hub.data_objects.ReviewObject;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReviewPersistenceStub implements ReviewPersistence{
    private List<ReviewObject> reviews;

    public ReviewPersistenceStub(){
        this.reviews = new ArrayList<>();

        reviews.add(new ReviewObject("nice movie","Iron man","Tom"));
        reviews.add(new ReviewObject("good movie","Iron man","Jack"));
        reviews.add(new ReviewObject("perfect","The Avangers","Tom"));
        reviews.add(new ReviewObject("bad movie","Thor","Roy"));
    }

    @Override
    public  List<ReviewObject> getReviewsSequential()  {
        return Collections.unmodifiableList(reviews);
    }

    @Override
    public List<ReviewObject> searchReview(String userName, String movieName){
        List<ReviewObject> newReviews;
        ReviewObject ReviewObjects;
        int index;
        int counter;
        newReviews = new ArrayList<>();
       for(counter=0;counter< reviews.size();counter++){
           ReviewObjects =  reviews.get(counter);
           if( ReviewObjects .getMovieName().equals(movieName)){
               if(ReviewObjects.getUserName().equals(userName)){
                   newReviews.add(reviews.get(counter));
               }
           }
       }
        return newReviews;
    }
    @Override
    public List<ReviewObject> getReviewsOfUser(String userName){
        List<ReviewObject> newReviews;
        ReviewObject ReviewObjects;
        int index;
        int counter;
        newReviews = new ArrayList<>();
        for(counter=0;counter< reviews.size();counter++){
            ReviewObjects =  reviews.get(counter);
            if(ReviewObjects.getUserName().equals(userName)){
                    newReviews.add(reviews.get(counter));
            }

        }
        return newReviews;
    }

    @Override
    public List<ReviewObject> getReviewsOfMovie(String movieName) {
        List<ReviewObject> newReviews;
        ReviewObject ReviewObjects;
        int index;
        int counter;
        newReviews = new ArrayList<>();
        for(counter=0;counter< reviews.size();counter++){
            ReviewObjects =  reviews.get(counter);
            if(ReviewObjects.getMovieName().equals(movieName)){
                newReviews.add(reviews.get(counter));
            }

        }
        return newReviews;
    }

    @Override
    public ReviewObject addNewReview(ReviewObject newReview) {
        // don't bother checking for duplicates
        reviews.add(newReview);
        return newReview;
    }

    @Override
    public ReviewObject updateReview(ReviewObject review) {
        int index;

        index = reviews.indexOf(review);
        if (index >= 0)
        {
          reviews.set(index, review);
        }
        return review;
    }

    @Override
    public void deleteReview(ReviewObject review) {
        int index;

        index = reviews.indexOf(review);
        if (index >= 0)
        {
            reviews.remove(index);
        }
    }
    @Override
    public void deleteAllReview(String userName) {
        ReviewObject ReviewObjects;
        int index;
        int counter;
        for(counter=0;counter< reviews.size();counter++){
            ReviewObjects =  reviews.get(counter);
            if(ReviewObjects.getUserName().equals(userName)){
                reviews.remove(reviews.get(counter));
            }

        }
    }

}
