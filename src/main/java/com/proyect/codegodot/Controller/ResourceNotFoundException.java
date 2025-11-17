package com.proyect.codegodot.Controller;

/**
 * Excepción lanzada cuando un recurso no es encontrado
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
