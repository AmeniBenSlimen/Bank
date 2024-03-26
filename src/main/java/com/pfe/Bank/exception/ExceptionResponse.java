package com.pfe.Bank.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ExceptionResponse {
    public LocalDateTime timestamp;
    public int status;
    public String message;
    public ExceptionResponse(LocalDateTime timestamp, int status, String message){
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public ExceptionResponse() {

    }
}
