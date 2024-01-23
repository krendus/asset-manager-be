package com.eprocess.assetmanager.exceptions;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String msg) {
        super(msg);
    }
}
