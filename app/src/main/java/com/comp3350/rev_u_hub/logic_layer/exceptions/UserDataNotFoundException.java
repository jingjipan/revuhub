package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class UserDataNotFoundException extends UserDataException {

    public UserDataNotFoundException() {}

    public UserDataNotFoundException(String message)
    {
        super(message);
    }
}