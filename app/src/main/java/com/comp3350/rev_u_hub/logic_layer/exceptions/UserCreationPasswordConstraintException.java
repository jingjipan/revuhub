package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class UserCreationPasswordConstraintException extends UserCreationException{

    public UserCreationPasswordConstraintException() {}

    public UserCreationPasswordConstraintException(String message)
    {
        super(message);
    }
}