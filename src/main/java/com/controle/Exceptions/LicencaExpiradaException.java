package com.controle.Exceptions;

import org.springframework.http.HttpStatus;

public class LicencaExpiradaException extends RuntimeException{
     String message;

    public LicencaExpiradaException(String message) {
        this(message, (Throwable)null);
    }

    public LicencaExpiradaException(String message, Throwable cause) {
        super(message, cause);
    }
}
