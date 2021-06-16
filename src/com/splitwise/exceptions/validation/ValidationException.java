package com.splitwise.exceptions.validation;

import com.splitwise.exceptions.SplitWiseException;

public class ValidationException extends SplitWiseException {
    public ValidationException(String message) {
        super(message);
    }
}
