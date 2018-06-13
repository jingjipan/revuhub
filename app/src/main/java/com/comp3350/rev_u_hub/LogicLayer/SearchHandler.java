package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.DMObjects.ReviewDMObject;

import java.io.File;
import java.util.ArrayList;

public class SearchHandler implements LogicInterface {
    private MovieSearcher myMovieSearcher;
    private ReviewProcessor myReviewProcessor;

    SearchHandler() {
        myMovieSearcher = new TypoEngine();
    }

    SearchHandler(MovieSearcher setMovieSearcher) {
        myMovieSearcher = setMovieSearcher;
    }

    SearchHandler(MovieSearcher setMovieSearcher, ReviewProcessor setReviewProcessor) {
        myMovieSearcher = setMovieSearcher;
        myReviewProcessor = setReviewProcessor;
    }

    SearchHandler(ReviewProcessor setReviewProcessor) {
        myReviewProcessor = setReviewProcessor;
    }

    public String getSynopsis(MovieDMObject movie) {return movie.getSynopsis();}

    public ArrayList<String> getCast(MovieDMObject movie) {return movie.getCast();}

    public File getPhoto(MovieDMObject movie) {return movie.getPhoto();}

    public String getNews(MovieDMObject movie) {return movie.getNews();}

    public ReviewDMObject getReview(MovieDMObject movie) {
        return myReviewProcessor.getReview(movie);
    }

    public boolean setReview(MovieDMObject movie, ReviewDMObject review) {
        return myReviewProcessor.setReview(movie, review);
    }

    public boolean setReview(MovieDMObject movie, String reviewText) {
        return myReviewProcessor.setReview(movie, reviewText);
    }

    public MovieDMObject getMovieSimple(String title) {
        return myMovieSearcher.getMovieSimple(title);
    }

    public MovieDMObject getMovie(String title) {
        return myMovieSearcher.getMovie(title);
    }
}