package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class MovieDataNoRatingsException extends MovieDataException {

    public MovieDataNoRatingsException() {}

    public MovieDataNoRatingsException(String message)
    {
        super(message);
    }
}