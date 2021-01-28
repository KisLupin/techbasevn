package com.techbasevn.backend.controller.exception;

import com.techbasevn.backend.common.BaseResponse;
import com.techbasevn.backend.exception.RestApiException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = RestApiException.class)
    public final ResponseEntity<BaseResponse> handleException(Exception ex) {
        logger.error(ex.toString());
        if (ex instanceof RestApiException) {
            RestApiException restApiException = (RestApiException) ex;
            return handleUserNotFoundException(restApiException);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Customize the response for RestApiException. */
    protected ResponseEntity<BaseResponse> handleUserNotFoundException(RestApiException ex) {
        return new ResponseEntity<>(new BaseResponse(ex), HttpStatus.BAD_REQUEST);
    }

}
