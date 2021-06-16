package com.splitwise.exceptions.validation;

public class DuplicateUsernameException extends ValidationException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
