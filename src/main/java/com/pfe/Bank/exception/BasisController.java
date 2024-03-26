package com.pfe.Bank.exception;



import org.hibernate.PessimisticLockException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.sql.SQLException;
import java.time.LocalDateTime;

//ce controller qui travaille sur plusieur exception
//@ControllerAdvice : lezem n7otha bech yefheemha w yraja3 l comportement spécifique sinon yraja3 l comportement par défaut

@ControllerAdvice
public class BasisController extends ResponseEntityExceptionHandler {

//Exception métier ena sna3tha
    @ExceptionHandler(MissingEntity.class)
    public ResponseEntity<ExceptionResponse> handleMissingEntity(MissingEntity e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setStatus(404);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);
    }
//vérifier le status de la base de donnée (active, inactive)
//Exception systéme
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ExceptionResponse> handleConnectException(ConnectException e) {
        //send notification / email to admin
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setStatus(500);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);

    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponse> handleConnectException(SQLException e) {
        //send notification / email to admin
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setStatus(500);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);

    }
    @ExceptionHandler(DuplicateEntity.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateException(DuplicateEntity e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(e.getMessage());
        response.setStatus(500);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.badRequest().body(response);

    }
}