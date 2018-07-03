package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class MovieDataNotFoundException extends MovieDataException {

    public MovieDataNotFoundException() {}

    public MovieDataNotFoundException(String message)
    {
        super(message);
    }
}