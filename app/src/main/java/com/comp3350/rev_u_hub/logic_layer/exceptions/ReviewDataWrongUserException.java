package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class ReviewDataWrongUserException extends ReviewDataException {

    public ReviewDataWrongUserException() {}

    public ReviewDataWrongUserException(String message)
    {
        super(message);
    }
}