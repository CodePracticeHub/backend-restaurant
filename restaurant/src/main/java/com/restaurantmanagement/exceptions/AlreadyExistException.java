package com.restaurantmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AlreadyExistException(String message){
        super(message);
    }
}
