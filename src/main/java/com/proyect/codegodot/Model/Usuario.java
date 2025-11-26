package com.proyect.codegodot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String correo;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "foto_url", columnDefinition = "LONGTEXT")
    private String fotoUrl;
    
    @Column(name = "fecha_registro")
    private String fechaRegistro;
    
    @Column(name = "actualizado_en")
    private String actualizadoEn;
}
