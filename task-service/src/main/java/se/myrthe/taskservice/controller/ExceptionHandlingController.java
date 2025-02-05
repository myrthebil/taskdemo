package se.myrthe.taskservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import se.myrthe.taskservice.exceptions.InvalidRequestBodyException;

public class ExceptionHandlingController {

    @ResponseStatus(value= HttpStatus.BAD_REQUEST,
            reason="Bad request")  // 400
    @ExceptionHandler(InvalidRequestBodyException.class)
    public void badRequest() {
        // Nothing to do
    }
}
