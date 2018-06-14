package com.comp3350.rev_u_hub.Application;

import com.comp3350.rev_u_hub.PersistenceLayer.MoviePersistence;
import com.comp3350.rev_u_hub.PersistenceLayer.stubs.MoviePersistenceStub;

public class Services {

    private static MoviePersistence moviePersistence = null;

    public static MoviePersistence getmoviePersistence()
    {
        if (moviePersistence == null)
        {
            moviePersistence = new MoviePersistenceStub();
        }

        return moviePersistence;
    }
}
