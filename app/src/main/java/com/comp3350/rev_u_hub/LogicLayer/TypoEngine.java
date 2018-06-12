package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.DMObjects.MovieDMObject;
import com.comp3350.rev_u_hub.PersistenceLayer.PersistenceInterface;
import com.comp3350.rev_u_hub.PersistenceLayer.fakeStorage;

public class TypoEngine {
    private PersistenceInterface myPersistenceLayer;

    TypoEngine() {
        myPersistenceLayer = new fakeStorage();
    }

    public MovieDMObject searchDefault(String title) {
        return searchSimple(title);
    }

    private MovieDMObject searchSimple(String title) {
        return new MovieDMObject(myPersistenceLayer.searchMovie(title));
    }

    private MovieDMObject searchAdvanced(ValidationRange range) {
        return null;
    }

    private MovieDMObject deletionTransposition() {
        return null;
    }

    private MovieDMObject insertionSubstitution(ValidationRange range) {
        return null;
    }
}