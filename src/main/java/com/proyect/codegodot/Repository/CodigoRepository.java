package com.proyect.codegodot.Repository;

import com.proyect.codegodot.Model.Codigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Codigo
 * Proporciona métodos CRUD automáticos y consultas personalizadas
 */
@Repository
public interface CodigoRepository extends JpaRepository<Codigo, Long> {

    /**
     * Busca códigos por título (búsqueda parcial, sin importar mayúsculas/minúsculas)
     * @param titulo El texto a buscar en el título
     * @return Lista de códigos que coinciden con la búsqueda
     */
    List<Codigo> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca códigos por descripción (búsqueda parcial, sin importar mayúsculas/minúsculas)
     * @param descripcion El texto a buscar en la descripción
     * @return Lista de códigos que coinciden con la búsqueda
     */
    List<Codigo> findByDescripcionContainingIgnoreCase(String descripcion);
}
