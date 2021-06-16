package com.splitwise.exceptions.authentication;

import com.splitwise.exceptions.SplitWiseException;

public class PasswordDoesNotMatchException extends SplitWiseException {
    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}
