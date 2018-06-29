package com.comp3350.rev_u_hub.Application;

import com.comp3350.rev_u_hub.logic_layer.interfaces.AccountManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.ReviewManager;
import com.comp3350.rev_u_hub.logic_layer.interfaces.UserLogin;
import com.comp3350.rev_u_hub.logic_layer.interfaces.MovieAccess;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;
import com.comp3350.rev_u_hub.persistence_layer.MoviePersistence;

public class Services {

    private static UserLogin userLogin = null;

    public static MovieAccess getMovieAccess() {
        return new MovieSearchEngine();
    }

    public static MovieAccess getMovieAccess(MoviePersistence persistenceLayer) {
        return new MovieSearchEngine(persistenceLayer);
    }

    public static AccountManager getAccountManager() {return null;} //temporary until implemented

    public static UserLogin getUserLogin() {return userLogin;} //temporary until implemented

    public static ReviewManager getReviewManager() {return null;} //temporary until implemented
}
