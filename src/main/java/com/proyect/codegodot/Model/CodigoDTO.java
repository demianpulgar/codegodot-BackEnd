package com.proyect.codegodot.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferir datos de Codigo entre capas
 * Se usa para enviar/recibir datos en formato JSON sin exponer la entidad directamente
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodigoDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String fecha;
    private String autor;
    private Integer likes;
    private Integer guardados;
    private String codigo;
}
