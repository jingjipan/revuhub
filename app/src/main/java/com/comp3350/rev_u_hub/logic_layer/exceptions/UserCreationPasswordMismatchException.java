package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class UserCreationPasswordMismatchException extends UserCreationException {

    public UserCreationPasswordMismatchException() {}

    public UserCreationPasswordMismatchException(String message)
    {
        super(message);
    }
}