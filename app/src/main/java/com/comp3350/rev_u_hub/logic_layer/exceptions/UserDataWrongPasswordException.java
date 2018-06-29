package com.comp3350.rev_u_hub.logic_layer.exceptions;

public class UserDataWrongPasswordException extends UserDataException {

    public UserDataWrongPasswordException() {}

    public UserDataWrongPasswordException(String message)
    {
        super(message);
    }
}