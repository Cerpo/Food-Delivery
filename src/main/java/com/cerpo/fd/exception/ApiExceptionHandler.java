package com.cerpo.fd.exception;

import com.cerpo.fd.AppUtils;
import com.cerpo.fd.payload.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(FDApiException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(FDApiException exception) {
        var httpStatus = exception.getHttpStatus();
        var apiResponse = new ExceptionResponse(httpStatus, exception.getMessage(), AppUtils.getDate(null));
        return new ResponseEntity<>(apiResponse, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(AuthenticationException exception) {
        var apiResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, "Bad credentials", AppUtils.getDate(null));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
