package com.comp3350.rev_u_hub_tests;

import com.comp3350.rev_u_hub.data_objects.ReviewObject;

public abstract class ReviewTestHelper extends UnitTestHelper {

    public static final String[] TEST_DESCRIPTORS = {"a random Review",
            "a random Review with numbers and symbols",
            "no Review",
            "a random Review with zero length contents",
            "a random Review with short contents",
            "a random Review with long contents"};

    public static ReviewObject randomReview(String movie, String user) {
        return randomReviewObject(movie,user);
    }

    // allChars = all characters, not just words
    public static ReviewObject randomReview(String movie, String user, boolean allChars) {
        return randomReviewObject(movie,user,allChars);
    }

    public static ReviewObject randomReview(String movie, String user, int length) {
        return randomReviewObject(movie,user,length);
    }

    // allChars = all characters, not just words
    public static ReviewObject randomReview(String movie, String user, boolean allChars, int length) {
        return randomReviewObject(movie,user,allChars,length);
    }

    public static ReviewObject[] getReviewTestArray(String movie, String user) {
        return new ReviewObject[]{
                ReviewTestHelper.randomReview(movie,user,false),
                ReviewTestHelper.randomReview(movie,user,true),
                new ReviewObject(),
                ReviewTestHelper.randomReview(movie,user,0),
                ReviewTestHelper.randomReview(movie,user,true,1),
                ReviewTestHelper.randomReview(movie,user,true,168)
        };
    }

    public static ReviewObject randomReviewObject(String movie, String user) {
        return randomReviewObject(movie,user,false, ReviewTestHelper.randomInteger());
    }

    public static ReviewObject randomReviewObject(String movie, String user, boolean allChars) {
        return randomReviewObject(movie,user,allChars, ReviewTestHelper.randomInteger());
    }

    public static ReviewObject randomReviewObject(String movie, String user, int length) {
        return randomReviewObject(movie,user,false,length);
    }

    // allChars = all characters, not just words
    public static ReviewObject randomReviewObject(String movie, String user, boolean allChars, int length) {
        ReviewObject randReview;
        if (allChars) {
            randReview = new ReviewObject(
                    randomString(length),
                    movie,
                    user
            );
        } else {
            randReview = new ReviewObject(
                    randomWord(length),
                    movie,
                    user
            );
        }
        return randReview!=null && !randReview.isEmpty() ? randReview : new ReviewObject();
    }
}