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

/**
 * Entidad JPA que representa un código de Godot Engine (GDScript)
 * Mapea a la tabla 'codigos' en la base de datos MySQL
 */
@Entity
@Table(name = "codigos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Codigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "categoria", length = 100)
    private String categoria;

    @Column(name = "fecha", length = 50)
    private String fecha;

    @Column(name = "autor", length = 100)
    private String autor;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "guardados")
    private Integer guardados;

    @Column(name = "codigo", columnDefinition = "LONGTEXT")
    private String codigo;
}
