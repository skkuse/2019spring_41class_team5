package edu.skku.dealistic.controller;

import edu.skku.dealistic.exception.NotFoundException;
import edu.skku.dealistic.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FailResponseController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse notFoundException() {
        return new ApiResponse("Not Found");
    }
}
