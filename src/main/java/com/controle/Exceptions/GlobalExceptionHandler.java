package com.controle.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LicencaExpiradaException.class)
    public ResponseEntity<String> handleLicencaExpiradaException(LicencaExpiradaException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    // Handler genérico para outras exceções não mapeadas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        if(ex.getMessage().contains("Bad credentials"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email ou senha inválidos");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno no servidor.");
    }
}