package com.comp3350.rev_u_hub.Application;

import com.comp3350.rev_u_hub.logic_layer.MovieAccess;
import com.comp3350.rev_u_hub.logic_layer.MovieSearchEngine;

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

    public static synchronized MoviePersistence getMoviePersistence()
    {
        if (moviePersistence == null)
        {
            moviePersistence = new MovieHSQLDB(Main.getDBPathName());
        }

        return moviePersistence;
    }

    public static synchronized ReviewPersistence getReviewPersistence()
    {
        if (reviewPersistence == null)
        {
            reviewPersistence = new ReviewHSQLDB(Main.getDBPathName());
        }

        return reviewPersistence;
    }

    public static synchronized UserPersistence getUserPersistence() {
        if (userPersistence == null)
        {
            userPersistence = new UserAccountHSQLDB(Main.getDBPathName());
        }

        return userPersistence;
    }
}

