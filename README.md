# CodeGodot Backend 🎮

API REST para gestionar códigos de Godot Engine (GDScript).

## 🚀 Tecnologías

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **MySQL 8**
- **Lombok**
- **Maven**

## 📋 Requisitos previos

- Java JDK 17 o superior
- Maven 3.6+
- MySQL 8.0+
- Base de datos `codegodot_db` creada

## ⚙️ Configuración

1. Crear la base de datos en MySQL:

```sql
CREATE DATABASE codegodot_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER 'codegodot_user'@'localhost' IDENTIFIED BY 'tu_password';
GRANT ALL PRIVILEGES ON codegodot_db.* TO 'codegodot_user'@'localhost';
FLUSH PRIVILEGES;
```

2. Crear la tabla `codigos`:

```sql
USE codegodot_db;

CREATE TABLE codigos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    codigo TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    fecha_actualizacion DATETIME NOT NULL
);
```

3. Configurar el archivo `application.properties`:

```properties
spring.datasource.password=TU_PASSWORD_AQUI
```

## 🏃 Ejecutar el proyecto

```bash
# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

## 📌 Estructura del proyecto

```
src/main/java/com/proyect/codegodot/
├── CodegodotApplication.java    # Clase principal
├── Controller/                  # Controladores REST
├── Service/                     # Lógica de negocio
├── Repository/                  # Acceso a datos
└── Model/                       # Entidades JPA
```

## 🔧 Próximos pasos

- [ ] Implementar entidades y repositorios
- [ ] Crear servicios de negocio
- [ ] Desarrollar controladores REST
- [ ] Agregar manejo de excepciones
- [ ] Implementar testing

---

**Autor:** DemianPulgar  
**Versión:** 1.0.0
