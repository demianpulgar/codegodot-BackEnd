-- =========================================
-- CREAR TABLA DE USUARIOS
-- =========================================

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100),
    apellido_paterno VARCHAR(100),
    apellido_materno VARCHAR(100),
    telefono VARCHAR(20),
    foto_url VARCHAR(500),
    fecha_registro DATETIME,
    actualizado_en DATETIME,
    
    -- Índices para búsquedas rápidas
    INDEX idx_username (username),
    INDEX idx_correo (correo),
    
    CONSTRAINT chk_username_not_empty CHECK (LENGTH(TRIM(username)) > 0),
    CONSTRAINT chk_correo_format CHECK (correo LIKE '%@%.%'),
    CONSTRAINT chk_password_not_empty CHECK (LENGTH(password) > 0)
);

-- =========================================
-- AGREGAR ÍNDICE PARA MEJOR RENDIMIENTO
-- =========================================

CREATE INDEX IF NOT EXISTS idx_usuario_username_correo ON usuarios(username, correo);

-- =========================================
-- MOSTRAR ESTRUCTURA DE LA TABLA
-- =========================================

DESCRIBE usuarios;
