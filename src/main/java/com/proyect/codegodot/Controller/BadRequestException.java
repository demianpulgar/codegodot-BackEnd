package com.proyect.codegodot.Controller;

/**
 * Excepción lanzada cuando la petición contiene datos inválidos
 */
public class BadRequestException extends RuntimeException {
    
    public BadRequestException(String message) {
        super(message);
    }
    
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
