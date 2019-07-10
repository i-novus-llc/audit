package ru.i_novus.ms.audit.rest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String processValidationError(Exception ex) {
        ex.printStackTrace();
        String result = ex.getMessage();
        return result;
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String processValidationError(BindException ex){
        ex.printStackTrace();
        String result = Objects.nonNull(ex.getFieldError())? ex.getFieldError().getField() : ex.getMessage();
        return "Неверный формат данных " + result;
    }
}