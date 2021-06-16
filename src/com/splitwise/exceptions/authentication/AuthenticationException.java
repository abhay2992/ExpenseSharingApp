package com.splitwise.exceptions.authentication;

import com.splitwise.exceptions.SplitWiseException;

public class AuthenticationException extends SplitWiseException {
    public AuthenticationException(String message) {
        super(message);
    }
}
