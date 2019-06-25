package edu.skku.dealistic.controller;

import edu.skku.dealistic.exception.NotFoundException;
import edu.skku.dealistic.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Fallback controller when error is occured.
 * Main Features:
 * - Provide error response
 *
 * @author Junhyun Kim
 */
@RestControllerAdvice
public class FailResponseController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse notFoundException() {
        return ApiResponse.builder().message("Not Found").build();
    }
}
