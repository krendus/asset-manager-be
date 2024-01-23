package com.eprocess.assetmanager.exceptions;

public class EmailTakenException extends RuntimeException {
    public EmailTakenException(String msg) {
        super(msg);
    }
}
