package com.davidbneto.votacao.controller;

import com.davidbneto.votacao.exception.CPFValidationException;
import com.davidbneto.votacao.exception.InvalidVoteException;
import com.davidbneto.votacao.exception.PautaException;
import com.davidbneto.votacao.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PautaException.class)
    public ResponseEntity<ErrorResponse> handlePautaException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidVoteException.class, CPFValidationException.class})
    public ResponseEntity<ErrorResponse> handleVotoException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handlePautaNotFound(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }

}
