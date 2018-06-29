package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class UserCreationFailedException extends UserCreationException {

    public UserCreationFailedException() {}

    public UserCreationFailedException(String message)
    {
        super(message);
    }
}