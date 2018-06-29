package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class UserCreationDuplicateException extends UserCreationException{

    public UserCreationDuplicateException() {}

    public UserCreationDuplicateException(String message)
    {
        super(message);
    }
}