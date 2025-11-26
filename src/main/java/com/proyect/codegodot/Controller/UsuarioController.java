package com.proyect.codegodot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.codegodot.Model.Usuario;
import com.proyect.codegodot.Model.UsuarioDTO;
import com.proyect.codegodot.Service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "${cors.allowed.origins:http://localhost:5173,http://localhost:3000}")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    /**
     * GET /api/usuarios/{username}
     * Obtener usuario por username
     */
    @GetMapping("/{username}")
    public ResponseEntity<UsuarioDTO> obtenerPorUsername(@PathVariable String username) {
        UsuarioDTO usuario = usuarioService.obtenerPorUsername(username);
        return ResponseEntity.ok(usuario);
    }
    
    /**
     * GET /api/usuarios/id/{id}
     * Obtener usuario por ID
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }
    
    /**
     * POST /api/usuarios
     * Crear nuevo usuario
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody Usuario usuario) {
        UsuarioDTO usuarioCreado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(201).body(usuarioCreado);
    }
    
    /**
     * PUT /api/usuarios/{username}
     * Actualizar usuario
     */
    @PutMapping("/{username}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable String username,
            @RequestBody Usuario usuarioActualizado) {
        UsuarioDTO usuarioUpdated = usuarioService.actualizarUsuario(username, usuarioActualizado);
        return ResponseEntity.ok(usuarioUpdated);
    }
    
    /**
     * GET /api/usuarios/check-username/{username}
     * Verificar si un username ya existe
     */
    @GetMapping("/check-username/{username}")
    public ResponseEntity<java.util.Map<String, Boolean>> checkUsername(@PathVariable String username) {
        boolean exists = usuarioService.usernameExists(username);
        return ResponseEntity.ok(java.util.Collections.singletonMap("exists", exists));
    }
}
