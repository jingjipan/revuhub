package com.comp3350.rev_u_hub.LogicLayer;

import com.comp3350.rev_u_hub.PersistenceLayer.PersistenceInterface;

public abstract class InitializeBackend {
    public static LogicInterface createLogicLayer() {
        return new SearchHandler();
    }

    public static LogicInterface createLogicLayer(PersistenceInterface persistenceLayer) {
        return new SearchHandler(new TypoEngine(persistenceLayer));
    }
}