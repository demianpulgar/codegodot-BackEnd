package com.proyect.codegodot.Service;

import com.proyect.codegodot.Controller.BadRequestException;
import com.proyect.codegodot.Controller.ResourceNotFoundException;
import com.proyect.codegodot.Model.Codigo;
import com.proyect.codegodot.Model.CodigoDTO;
import com.proyect.codegodot.Repository.CodigoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Codigo
 * Contiene toda la lógica de negocio
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CodigoServiceImpl implements CodigoService {

    private final CodigoRepository codigoRepository;
    private final CodigoMapper codigoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CodigoDTO> obtenerTodos() {
        log.info("Obteniendo todos los códigos");
        return codigoRepository.findAll()
                .stream()
                .map(codigoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CodigoDTO obtenerPorId(Long id) {
        log.info("Obteniendo código con ID: {}", id);
        Codigo codigo = codigoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Código con ID {} no encontrado", id);
                    return new ResourceNotFoundException("Código no encontrado con ID: " + id);
                });
        return codigoMapper.toDTO(codigo);
    }

    @Override
    @Transactional
    public CodigoDTO crear(CodigoDTO codigoDTO) {
        log.info("Creando nuevo código: {}", codigoDTO.getTitulo());
        
        // Validaciones
        if (codigoDTO.getTitulo() == null || codigoDTO.getTitulo().trim().isEmpty()) {
            throw new BadRequestException("El título es obligatorio");
        }
        if (codigoDTO.getDescripcion() == null || codigoDTO.getDescripcion().trim().isEmpty()) {
            throw new BadRequestException("La descripción es obligatoria");
        }
        if (codigoDTO.getCodigo() == null || codigoDTO.getCodigo().trim().isEmpty()) {
            throw new BadRequestException("El código es obligatorio");
        }

        Codigo codigo = codigoMapper.toEntity(codigoDTO);
        Codigo codigoGuardado = codigoRepository.save(codigo);
        log.info("Código creado exitosamente con ID: {}", codigoGuardado.getId());
        
        return codigoMapper.toDTO(codigoGuardado);
    }

    @Override
    @Transactional
    public CodigoDTO actualizar(Long id, CodigoDTO codigoDTO) {
        log.info("Actualizando código con ID: {}", id);
        
        Codigo codigoExistente = codigoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Código con ID {} no encontrado para actualizar", id);
                    return new ResourceNotFoundException("Código no encontrado con ID: " + id);
                });

        // Validaciones
        if (codigoDTO.getTitulo() == null || codigoDTO.getTitulo().trim().isEmpty()) {
            throw new BadRequestException("El título es obligatorio");
        }
        if (codigoDTO.getDescripcion() == null || codigoDTO.getDescripcion().trim().isEmpty()) {
            throw new BadRequestException("La descripción es obligatoria");
        }
        if (codigoDTO.getCodigo() == null || codigoDTO.getCodigo().trim().isEmpty()) {
            throw new BadRequestException("El código es obligatorio");
        }

        // Actualizar campos
        codigoExistente.setTitulo(codigoDTO.getTitulo());
        codigoExistente.setDescripcion(codigoDTO.getDescripcion());
        codigoExistente.setCodigo(codigoDTO.getCodigo());
        // fechaActualizacion se actualiza automáticamente con @UpdateTimestamp

        Codigo codigoActualizado = codigoRepository.save(codigoExistente);
        log.info("Código actualizado exitosamente con ID: {}", id);
        
        return codigoMapper.toDTO(codigoActualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando código con ID: {}", id);
        
        if (!codigoRepository.existsById(id)) {
            log.error("Código con ID {} no encontrado para eliminar", id);
            throw new ResourceNotFoundException("Código no encontrado con ID: " + id);
        }

        codigoRepository.deleteById(id);
        log.info("Código eliminado exitosamente con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodigoDTO> buscarPorTitulo(String titulo) {
        log.info("Buscando códigos por título: {}", titulo);
        
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new BadRequestException("El término de búsqueda no puede estar vacío");
        }

        return codigoRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(codigoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
