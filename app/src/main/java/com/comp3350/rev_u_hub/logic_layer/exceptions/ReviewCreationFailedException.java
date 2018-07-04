package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class ReviewCreationFailedException extends ReviewCreationException {

    public ReviewCreationFailedException() {}

    public ReviewCreationFailedException(String message)
    {
        super(message);
    }
}