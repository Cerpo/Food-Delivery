package com.cerpo.fd.exception;

import com.cerpo.fd.AppUtils;
import com.cerpo.fd.payload.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(FDApiException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> resolveException(FDApiException exception) {
        var exceptionResponse = new ExceptionResponse(exception.getHttpStatus(), exception.getMessage(), AppUtils.getDate(null));
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.httpStatus());
    }
}
