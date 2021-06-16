package com.splitwise.exceptions.notfound;

import com.splitwise.exceptions.notfound.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
