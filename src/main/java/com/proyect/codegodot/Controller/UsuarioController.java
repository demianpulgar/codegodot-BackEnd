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
import org.springframework.web.bind.annotation.RequestParam;
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
     * POST /api/usuarios/login
     * Login de usuario - retorna datos del usuario si credenciales son válidas
     */
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody Usuario loginRequest) {
        UsuarioDTO usuario = usuarioService.login(loginRequest.getUsername(), loginRequest.getCorreo(), loginRequest.getPassword());
        return ResponseEntity.ok(usuario);
    }
    
    /**
     * PUT /api/usuarios/{username}
     * Actualizar usuario - Acepta JSON, form-urlencoded y multipart/form-data
     */
    @PutMapping(value = "/{username}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable String username,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellidoPaterno,
            @RequestParam(required = false) String apellidoMaterno,
            @RequestParam(required = false) String correo,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String fotoUrl,
            @RequestParam(name = "username", required = false) String nuevoUsername) {
        
        // Crear objeto Usuario con los parámetros que fueron enviados
        Usuario usuarioActualizado = new Usuario();
        if (nombre != null) usuarioActualizado.setNombre(nombre);
        if (apellidoPaterno != null) usuarioActualizado.setApellidoPaterno(apellidoPaterno);
        if (apellidoMaterno != null) usuarioActualizado.setApellidoMaterno(apellidoMaterno);
        if (correo != null) usuarioActualizado.setCorreo(correo);
        if (telefono != null) usuarioActualizado.setTelefono(telefono);
        if (fotoUrl != null) usuarioActualizado.setFotoUrl(fotoUrl);
        if (nuevoUsername != null) usuarioActualizado.setUsername(nuevoUsername);
        
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
