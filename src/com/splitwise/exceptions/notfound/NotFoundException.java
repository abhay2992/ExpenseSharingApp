package com.splitwise.exceptions.notfound;

import com.splitwise.exceptions.SplitWiseException;

public class NotFoundException extends SplitWiseException {
    public NotFoundException(String message) {
        super(message);
    }
}
