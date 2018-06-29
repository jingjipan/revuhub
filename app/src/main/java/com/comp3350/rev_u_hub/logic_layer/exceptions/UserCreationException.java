package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class UserCreationException extends Exception {

    public UserCreationException() {}

    public UserCreationException(String message)
    {
        super(message);
    }
}