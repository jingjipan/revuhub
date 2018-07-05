package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class UserCreationUsernameConstraintException extends UserCreationException{

    public UserCreationUsernameConstraintException() {}

    public UserCreationUsernameConstraintException(String message)
    {
        super(message);
    }
}