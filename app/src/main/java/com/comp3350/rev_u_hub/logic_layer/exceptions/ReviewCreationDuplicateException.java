package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class ReviewCreationDuplicateException extends ReviewCreationException {

    public ReviewCreationDuplicateException() {}

    public ReviewCreationDuplicateException(String message)
    {
        super(message);
    }
}