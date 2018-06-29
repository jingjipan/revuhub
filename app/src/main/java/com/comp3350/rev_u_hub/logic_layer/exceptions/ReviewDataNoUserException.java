package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class ReviewDataNoUserException extends ReviewDataException {

    public ReviewDataNoUserException() {}

    public ReviewDataNoUserException(String message)
    {
        super(message);
    }
}