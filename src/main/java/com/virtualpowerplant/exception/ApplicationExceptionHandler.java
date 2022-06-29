package com.virtualpowerplant.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected @ResponseBody ErrorResponse handleBadRequest(final Exception exception,
                                                           final HttpServletRequest request){
        return new ErrorResponse(exception.getMessage(), request.getAttribute("requestId").toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected @ResponseBody ErrorResponse handleBadRequestBody(final Exception exception,
                                                           final HttpServletRequest request){
        return new ErrorResponse(exception.getMessage(), request.getAttribute("requestId").toString());
    }
}
