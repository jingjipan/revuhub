package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class ReviewCreationNoUserException extends ReviewCreationException {

    public ReviewCreationNoUserException() {}

    public ReviewCreationNoUserException(String message)
    {
        super(message);
    }
}