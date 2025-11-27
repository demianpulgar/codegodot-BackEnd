package com.proyect.codegodot.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.codegodot.Controller.ResourceNotFoundException;
import com.proyect.codegodot.Model.Usuario;
import com.proyect.codegodot.Model.UsuarioDTO;
import com.proyect.codegodot.Repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    /**
     * Obtener usuario por username
     */
    public UsuarioDTO obtenerPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        return convertirADTO(usuario);
    }
    
    /**
     * Obtener usuario por ID
     */
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return convertirADTO(usuario);
    }
    
    /**
     * Crear nuevo usuario
     */
    public UsuarioDTO crearUsuario(Usuario usuario) {
        // Validar que no exista usuario con mismo username o correo
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        
        usuario.setFechaRegistro(LocalDateTime.now().format(formatter));
        usuario.setActualizadoEn(LocalDateTime.now().format(formatter));
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }
    
    /**
     * Actualizar usuario
     */
    public UsuarioDTO actualizarUsuario(String username, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + username));
        
        // Permitir cambio de username con validación
        if (usuarioActualizado.getUsername() != null && 
            !usuarioActualizado.getUsername().equals(usuario.getUsername())) {
            if (usuarioRepository.existsByUsername(usuarioActualizado.getUsername())) {
                throw new IllegalArgumentException("El nombre de usuario ya existe");
            }
            usuario.setUsername(usuarioActualizado.getUsername());
        }
        
        // Actualizar campos
        if (usuarioActualizado.getNombre() != null) {
            usuario.setNombre(usuarioActualizado.getNombre());
        }
        if (usuarioActualizado.getApellidoPaterno() != null) {
            usuario.setApellidoPaterno(usuarioActualizado.getApellidoPaterno());
        }
        if (usuarioActualizado.getApellidoMaterno() != null) {
            usuario.setApellidoMaterno(usuarioActualizado.getApellidoMaterno());
        }
        if (usuarioActualizado.getTelefono() != null) {
            usuario.setTelefono(usuarioActualizado.getTelefono());
        }
        if (usuarioActualizado.getFotoUrl() != null) {
            // Validar tamaño de foto (máximo 500KB base64)
            String fotoUrl = usuarioActualizado.getFotoUrl();
            if (fotoUrl.length() > 700000) { // ~500KB en base64
                throw new IllegalArgumentException("La foto es demasiado grande. Máximo 500KB");
            }
            usuario.setFotoUrl(fotoUrl);
        }
        
        // No permitir cambio de correo sin validación
        if (usuarioActualizado.getCorreo() != null && 
            !usuarioActualizado.getCorreo().equals(usuario.getCorreo())) {
            if (usuarioRepository.existsByCorreo(usuarioActualizado.getCorreo())) {
                throw new IllegalArgumentException("El correo ya está registrado");
            }
            usuario.setCorreo(usuarioActualizado.getCorreo());
        }
        
        usuario.setActualizadoEn(LocalDateTime.now().format(formatter));
        
        Usuario usuarioActualiz = usuarioRepository.save(usuario);
        return convertirADTO(usuarioActualiz);
    }
    
    /**
     * Convertir Usuario a UsuarioDTO (sin password)
     */
    private UsuarioDTO convertirADTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getUsername(),
            usuario.getCorreo(),
            usuario.getNombre(),
            usuario.getApellidoPaterno(),
            usuario.getApellidoMaterno(),
            usuario.getTelefono(),
            usuario.getFotoUrl(),
            usuario.getFechaRegistro(),
            usuario.getActualizadoEn()
        );
    }
    
    /**
     * Verificar si un username ya existe
     */
    public boolean usernameExists(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    /**
     * Login de usuario - validar credenciales
     * Retorna datos del usuario si las credenciales son correctas
     */
    public UsuarioDTO login(String username, String correo, String password) {
        // Buscar usuario por username o correo
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseGet(() -> {
                    // Si no encuentra por username, intenta por correo
                    return usuarioRepository.findByCorreo(correo)
                            .orElse(null);
                });
        
        // Validar que existe el usuario
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario o correo no encontrado");
        }
        
        // Validar contraseña
        if (!usuario.getPassword().equals(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        
        // Retornar datos del usuario (sin password)
        return convertirADTO(usuario);
    }

    /**
     * Obtener lista de todos los usuarios (sin contraseña)
     */
    public java.util.List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(java.util.stream.Collectors.toList());
    }
}
