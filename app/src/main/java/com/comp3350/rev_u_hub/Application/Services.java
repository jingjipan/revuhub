package com.comp3350.rev_u_hub.Application;

import com.comp3350.rev_u_hub.logic_layer.AccountManagement;
import com.comp3350.rev_u_hub.logic_layer.CurrentUserStorage;
import com.comp3350.rev_u_hub.logic_layer.MovieListViewer;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.RatingManagement;
import com.comp3350.rev_u_hub.logic_layer.ReviewManagement;
import com.comp3350.rev_u_hub.logic_layer.ReviewQuery;
import com.comp3350.rev_u_hub.logic_layer.UserMovieStats;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieLists;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieRatings;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserMovieProfile;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserSearch;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;
import com.comp3350.rev_u_hub.persistence_layer.ReviewPersistence;
import com.comp3350.rev_u_hub.persistence_layer.UserPersistence;
import com.comp3350.rev_u_hub.persistence_layer.stubs.MovieHSQLDB;
import com.comp3350.rev_u_hub.persistence_layer.stubs.ReviewHSQLDB;
import com.comp3350.rev_u_hub.persistence_layer.stubs.UserAccountHSQLDB;

public class Services {

    private static MoviePersistence moviePersistence = null;
    private static ReviewPersistence reviewPersistence = null;
    private static UserPersistence userPersistence = null;

    private static MovieSearch movieSearch = null;
    private static UserSearch userSearch = null;
    private static ReviewSearch reviewSearch = null;

    private static UserLogin userLogin = null;
    private static MovieRatings movieRatings = null;
    private static AccountManager accountManager = null;
    private static ReviewManager reviewManager = null;

    private static MovieLists movieLists = null;
    private static UserMovieProfile userMovieProfile = null;

    private static synchronized MoviePersistence getMoviePersistence() {
        if (moviePersistence == null) moviePersistence = new MovieHSQLDB(Main.getDBPathName());
        return moviePersistence;
    }

    private static synchronized ReviewPersistence getReviewPersistence() {
        if (reviewPersistence == null) reviewPersistence = new ReviewHSQLDB(Main.getDBPathName());
        return reviewPersistence;
    }

    private static synchronized UserPersistence getUserPersistence() {
        if (userPersistence == null) userPersistence = new UserAccountHSQLDB(Main.getDBPathName());
        return userPersistence;
    }

    public static MovieSearch getMovieSearch() {
        if ( movieSearch == null ) movieSearch = new MovieSearchEngine(getMoviePersistence());
        return movieSearch;
    }

    public static UserSearch getUserSearch() {
        if ( userSearch == null ) userSearch = new UserSearchEngine(getUserPersistence());
        return userSearch;
    }

    public static ReviewSearch getReviewSearch() {
        if ( reviewSearch == null ) reviewSearch = new ReviewQuery(getReviewPersistence());
        return reviewSearch;
    }

    public static AccountManager getAccountManager() {
        if ( accountManager == null ) accountManager = new AccountManagement(getUserSearch(),
                getUserPersistence());
        return accountManager;
    }

    public static UserLogin getUserLogin() {
        if ( userLogin == null ) userLogin = new CurrentUserStorage(getUserSearch());
        return userLogin;
    }

    public static ReviewManager getReviewManager() {
        if ( reviewManager == null ) reviewManager = new ReviewManagement(getReviewPersistence());
        return reviewManager;
    }

    public static MovieRatings getMovieRatings() {
        if ( movieRatings == null ) movieRatings =
                new RatingManagement(getMovieSearch(), getMoviePersistence());
        return movieRatings;
    }

    public static MovieLists getMovieLists() {
        if ( movieLists == null ) movieLists =
                new MovieListViewer(getMoviePersistence());
        return movieLists;
    }

    public static UserMovieProfile getUserMovieProfile() {
        if ( userMovieProfile == null ) userMovieProfile =
                new UserMovieStats(getMovieSearch(), getUserSearch(),
                        getReviewSearch(), getMovieRatings());
        return userMovieProfile;
    }
}