package com.proyect.codegodot.Service;

import com.proyect.codegodot.Model.Codigo;
import com.proyect.codegodot.Model.CodigoDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Entity (Codigo) y DTO (CodigoDTO)
 * Separa la lógica de conversión del resto de la aplicación
 */
@Component
public class CodigoMapper {

    /**
     * Convierte una entidad Codigo a CodigoDTO
     * @param entity La entidad a convertir
     * @return El DTO correspondiente
     */
    public CodigoDTO toDTO(Codigo entity) {
        if (entity == null) {
            return null;
        }

        CodigoDTO dto = new CodigoDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setCodigo(entity.getCodigo());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());

        return dto;
    }

    /**
     * Convierte un CodigoDTO a entidad Codigo
     * @param dto El DTO a convertir
     * @return La entidad correspondiente
     */
    public Codigo toEntity(CodigoDTO dto) {
        if (dto == null) {
            return null;
        }

        Codigo entity = new Codigo();
        entity.setId(dto.getId());
        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setCodigo(dto.getCodigo());
        // Las fechas se manejan automáticamente con @CreationTimestamp y @UpdateTimestamp

        return entity;
    }
}
