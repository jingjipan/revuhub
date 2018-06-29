package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class ReviewDataNotFoundException extends ReviewDataException {

    public ReviewDataNotFoundException() {}

    public ReviewDataNotFoundException(String message)
    {
        super(message);
    }
}