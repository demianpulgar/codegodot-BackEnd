package com.proyect.codegodot.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String username;
    private String correo;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String fotoUrl;
    private String fechaRegistro;
    private String actualizadoEn;
    // No incluir password en el DTO de respuesta por seguridad
}
