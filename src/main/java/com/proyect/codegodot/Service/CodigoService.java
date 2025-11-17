package com.proyect.codegodot.Service;

import com.proyect.codegodot.Model.CodigoDTO;

import java.util.List;

/**
 * Interface del servicio de Codigo
 * Define los métodos de negocio disponibles
 */
public interface CodigoService {

    /**
     * Obtiene todos los códigos
     * @return Lista de todos los códigos
     */
    List<CodigoDTO> obtenerTodos();

    /**
     * Obtiene un código por su ID
     * @param id El ID del código
     * @return El código encontrado
     */
    CodigoDTO obtenerPorId(Long id);

    /**
     * Crea un nuevo código
     * @param codigoDTO Los datos del código a crear
     * @return El código creado
     */
    CodigoDTO crear(CodigoDTO codigoDTO);

    /**
     * Actualiza un código existente
     * @param id El ID del código a actualizar
     * @param codigoDTO Los nuevos datos del código
     * @return El código actualizado
     */
    CodigoDTO actualizar(Long id, CodigoDTO codigoDTO);

    /**
     * Elimina un código por su ID
     * @param id El ID del código a eliminar
     */
    void eliminar(Long id);

    /**
     * Busca códigos por título
     * @param titulo El texto a buscar en el título
     * @return Lista de códigos que coinciden con la búsqueda
     */
    List<CodigoDTO> buscarPorTitulo(String titulo);
}
