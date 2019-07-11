package ru.i_novus.ms.audit.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ControllerAdvice
public class RestErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestErrorHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String processValidationError(Exception e) {
        logger.error("Internal error", e);
        return e.getMessage();
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String processValidationError(BindException e){
        logger.error("Bad Request", e);
        String result = Objects.nonNull(e.getFieldError())? e.getFieldError().getField() : e.getMessage();
        return "Неверный формат данных " + result;
    }
}