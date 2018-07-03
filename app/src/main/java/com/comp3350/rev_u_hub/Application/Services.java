package com.comp3350.rev_u_hub.Application;

import com.comp3350.rev_u_hub.logic_layer.CurrentUserStorage;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.ReviewQuery;
import com.comp3350.rev_u_hub.logic_layer.UserSearchEngine;
import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewInfo;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieAccess;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserAccess;
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

    private static MovieAccess movieAccess = null;
    private static UserAccess userAccess = null;

    private static ReviewInfo reviewInfo = null;
    private static UserLogin userLogin = null;

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

    public static MovieAccess getMovieAccess() {
        if ( movieAccess == null ) movieAccess =  new MovieSearchEngine(getMoviePersistence());
        return movieAccess;
    }

    private static UserAccess getUserAccess() {
        if ( userAccess == null ) userAccess =  new UserSearchEngine(getUserPersistence());
        return userAccess;
    }

    public static ReviewInfo getReviewInfo() {
        if ( reviewInfo == null ) reviewInfo =  new ReviewQuery(getReviewPersistence());
        return reviewInfo;
    }

    public static AccountManager getAccountManager() {return null;} //temporary until implemented

    public static UserLogin getUserLogin() {
        if ( userLogin == null ) userLogin =  new CurrentUserStorage(getUserAccess());
        return userLogin;
    }

    public static ReviewManager getReviewManager() {return null;} //temporary until implemented
}