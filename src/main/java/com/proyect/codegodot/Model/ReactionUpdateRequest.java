package com.proyect.codegodot.Model;

import lombok.Data;

/**
 * Request body para actualizar contadores de reacciones (likes/guardados)
 */
@Data
public class ReactionUpdateRequest {
    /**
     * true para incrementar, false para decrementar
     */
    private boolean increment = true;
}
