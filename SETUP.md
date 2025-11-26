# CodeGodot Backend

Backend Spring Boot para la plataforma CodeGodot.

## Requisitos

- Java 21
- Maven 3.9+
- MySQL 8.0+

## Instalación

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd codegodot-BackEnd
```

### 2. Configurar Variables de Entorno

Copia el archivo `.env.example` a `.env`:

```bash
cp .env.example .env
```

Edita `.env` con tu configuración:

```properties
# Database
DB_HOST=your-database-host
DB_PORT=3306
DB_NAME=codegodot_db
DB_USERNAME=your_db_user
DB_PASSWORD=your_db_password

# Server
SERVER_PORT=8080

# CORS
CORS_ALLOWED_ORIGINS=http://localhost:5173,http://localhost:3000
```

### 3. Compilar el Proyecto

```bash
mvnw clean package -DskipTests
```

### 4. Ejecutar la Aplicación

```bash
# Development
java -jar target/codegodot-0.0.1-SNAPSHOT.jar

# Con variables de entorno específicas
java -Dspring.datasource.url=jdbc:mysql://tu-host:3306/tu-bd \
     -Dspring.datasource.username=usuario \
     -Dspring.datasource.password=contraseña \
     -jar target/codegodot-0.0.1-SNAPSHOT.jar
```

## API Endpoints

- `GET /api/codigos` - Obtener todos los códigos
- `GET /api/usuarios/{username}` - Obtener usuario por username
- `POST /api/usuarios` - Registrar nuevo usuario
- `POST /api/usuarios/login` - Login de usuario
- `PUT /api/usuarios/{username}` - Actualizar perfil de usuario

## Notas Importantes

- El archivo `.env` nunca debe ser commiteado a Git
- Usa `.env.example` como referencia para nuevos desarrolladores
- Las contraseñas de base de datos deben ser almacenadas de forma segura
- En producción, usa variables de entorno del sistema operativo o servicio de secretos
