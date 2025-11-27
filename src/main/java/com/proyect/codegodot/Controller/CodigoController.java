package com.proyect.codegodot.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.codegodot.Model.CodigoDTO;
import com.proyect.codegodot.Model.ReactionUpdateRequest;
import com.proyect.codegodot.Service.CodigoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador REST para gestionar códigos de Godot
 * Expone endpoints para operaciones CRUD y búsqueda
 */
@RestController
@RequestMapping("/api/codigos")
@RequiredArgsConstructor
@Slf4j
public class CodigoController {

    private final CodigoService codigoService;

    /**
     * GET /api/codigos
     * Obtiene todos los códigos
     */
    @GetMapping
    public ResponseEntity<List<CodigoDTO>> obtenerTodos() {
        log.info("GET /api/codigos - Obteniendo todos los códigos");
        List<CodigoDTO> codigos = codigoService.obtenerTodos();
        return ResponseEntity.ok(codigos);
    }

    /**
     * GET /api/codigos/search?titulo=xyz
     * Busca códigos por título
     * ⚠️ DEBE IR ANTES DE /{id} para evitar conflicto de rutas
     */
    @GetMapping("/search")
    public ResponseEntity<List<CodigoDTO>> buscarPorTitulo(@RequestParam String titulo) {
        log.info("GET /api/codigos/search?titulo={} - Buscando códigos por título", titulo);
        List<CodigoDTO> codigos = codigoService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(codigos);
    }

    /**
     * GET /api/codigos/{id}
     * Obtiene un código por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CodigoDTO> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/codigos/{} - Obteniendo código por ID", id);
        CodigoDTO codigo = codigoService.obtenerPorId(id);
        return ResponseEntity.ok(codigo);
    }

    /**
     * POST /api/codigos
     * Crea un nuevo código
     */
    @PostMapping
    public ResponseEntity<CodigoDTO> crear(@RequestBody CodigoDTO codigoDTO) {
        log.info("POST /api/codigos - Creando nuevo código: {}", codigoDTO.getTitulo());
        CodigoDTO codigoCreado = codigoService.crear(codigoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(codigoCreado);
    }

    /**
     * PUT /api/codigos/{id}
     * Actualiza un código existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<CodigoDTO> actualizar(
            @PathVariable Long id,
            @RequestBody CodigoDTO codigoDTO) {
        log.info("PUT /api/codigos/{} - Actualizando código", id);
        CodigoDTO codigoActualizado = codigoService.actualizar(id, codigoDTO);
        return ResponseEntity.ok(codigoActualizado);
    }

    /**
     * DELETE /api/codigos/{id}
     * Elimina un código por su ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/codigos/{} - Eliminando código", id);
        codigoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/codigos/{id}/likes
     * Incrementa o decrementa el contador de likes
     */
    @PostMapping("/{id}/likes")
    public ResponseEntity<CodigoDTO> actualizarLikes(@PathVariable Long id,
            @RequestBody ReactionUpdateRequest request) {
        log.info("POST /api/codigos/{}/likes - increment? {}", id, request.isIncrement());
        CodigoDTO actualizado = codigoService.actualizarLikes(id, request.isIncrement());
        return ResponseEntity.ok(actualizado);
    }

    /**
     * POST /api/codigos/{id}/guardados
     * Incrementa o decrementa el contador de guardados
     */
    @PostMapping("/{id}/guardados")
    public ResponseEntity<CodigoDTO> actualizarGuardados(@PathVariable Long id,
            @RequestBody ReactionUpdateRequest request) {
        log.info("POST /api/codigos/{}/guardados - increment? {}", id, request.isIncrement());
        CodigoDTO actualizado = codigoService.actualizarGuardados(id, request.isIncrement());
        return ResponseEntity.ok(actualizado);
    }
}
