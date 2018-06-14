package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.DMObjects.ReviewDMObject;

import java.util.ArrayList;

public class SearchHandler implements LogicInterface {
    private MovieSearcher myMovieSearcher;
    private ReviewProcessor myReviewProcessor;
    private MovieInfoGetter myMovieInfoGetter;

    SearchHandler() {
        buildSearchHandler();
    }

    SearchHandler(LogicComponent first) {
        install(first);
        buildSearchHandler();
    }

    SearchHandler(LogicComponent first, LogicComponent second) {
        install(first);
        install(second);
        buildSearchHandler();
    }

    SearchHandler(LogicComponent first, LogicComponent second, LogicComponent third) {
        install(first);
        install(second);
        install(third);
        buildSearchHandler();
    }

    public String getSynopsis(MovieDMObject movie) {return myMovieInfoGetter.getSynopsis(movie);}

    public ArrayList<String> getCast(MovieDMObject movie) {return myMovieInfoGetter.getCast(movie);}

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

    private void buildSearchHandler() {
        if (myMovieSearcher==null) myMovieSearcher = new TypoEngine();
        if (myReviewProcessor==null) myReviewProcessor = new ReviewManager();
        if (myMovieInfoGetter==null) myMovieInfoGetter = new MovieInfoConverter();
    }

    private void install(LogicComponent component) {
        if (component instanceof MovieSearcher)
            myMovieSearcher = (MovieSearcher) component;
        if (component instanceof ReviewProcessor)
            myReviewProcessor = (ReviewProcessor) component;
        if (component instanceof MovieInfoGetter)
            myMovieInfoGetter = (MovieInfoGetter) component;
    }
}