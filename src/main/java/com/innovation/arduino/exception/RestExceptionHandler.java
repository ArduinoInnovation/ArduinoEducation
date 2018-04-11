package com.innovation.arduino.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description:
 *
 * @author xxz
 * Created on 03/20/2018
 * <p>
 * Update:
 * @author xxz
 * Last modified on
 */
@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public RestErrorInformation nfHandler(NotFoundException e) {
        return new RestErrorInformation(e.getMessage());
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(NullPointerException.class)
//    public RestErrorInformation npHandler(NullPointerException e) {
//        return new RestErrorInformation(Arrays.toString(e.getStackTrace()));
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputException.class)
    public RestErrorInformation invalidInputHandler(InvalidInputException e) {
        return new RestErrorInformation(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RestErrorInformation messageErrorHandler(HttpMessageNotReadableException e) {
        return new RestErrorInformation(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PermissionDeniedException.class)
    public RestErrorInformation permissionErrorHandler(PermissionDeniedException e) {
        return new RestErrorInformation(e.getMessage());
    }

}
