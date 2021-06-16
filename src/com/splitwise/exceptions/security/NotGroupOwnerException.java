package com.splitwise.exceptions.security;

public class NotGroupOwnerException extends SecurityException {
    public NotGroupOwnerException(String message) {
        super(message);
    }
}
