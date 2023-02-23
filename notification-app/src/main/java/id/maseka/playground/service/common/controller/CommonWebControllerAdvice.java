package id.maseka.playground.service.common.controller;

import id.maseka.playground.service.common.error.GenericErrorException;
import id.maseka.playground.service.common.error.ValidationErrorException;
import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.transaction.TransactionException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class CommonWebControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    ErrorResponse handleConstraintViolation(ConstraintViolationException exception) {
        var invalidParam = exception.getConstraintViolations().stream().collect(Collectors.groupingBy(entryKey -> entryKey.getPropertyPath().toString(), Collectors.mapping(ConstraintViolation::getMessage, Collectors.joining(","))));

        return new ValidationErrorException("fail to validate constraints", invalidParam);
    }

    @ExceptionHandler(RollbackException.class)
    ErrorResponse handleRollback(RollbackException exception) {
        if(exception.getCause() instanceof ConstraintViolationException constraintViolationException) {
            return handleConstraintViolation(constraintViolationException);
        }

        return defaultHandle();
    }

    @ExceptionHandler(TransactionException.class)
    ErrorResponse handleTransaction(TransactionException exception) {
        if(exception.getCause() instanceof RollbackException rollbackException) {
            return handleRollback(rollbackException);
        } else if(exception.getCause() instanceof ConstraintViolationException constraintViolationException) {
            return handleConstraintViolation(constraintViolationException);
        }

        return defaultHandle();
    }

    @ExceptionHandler(Exception.class)
    ErrorResponse defaultHandle() {

        return new GenericErrorException();
    }

}
