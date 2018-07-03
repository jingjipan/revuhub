package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class MovieDataInvalidRatingException extends MovieDataException {

    public MovieDataInvalidRatingException() {}

    public MovieDataInvalidRatingException(String message)
    {
        super(message);
    }
}