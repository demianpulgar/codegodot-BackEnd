package com.proyect.codegodot.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.proyect.codegodot.Model.Codigo;
import com.proyect.codegodot.Repository.CodigoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuración para cargar datos iniciales en la base de datos
 * SOLO se ejecuta si el perfil activo es "dev"
 * Para desactivarlo, cambia spring.profiles.active en application.properties
 */
@Configuration
@Profile("dev")
@Slf4j
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeData(CodigoRepository codigoRepository) {
        return args -> {
            log.info("🚀 Iniciando carga de datos de códigos...");

            // Verificar si ya existen datos
            long count = codigoRepository.count();
            if (count > 0) {
                log.warn("⚠️ La tabla 'codigos' ya contiene {} registros. Saltando inicialización.", count);
                return;
            }

            log.info("📝 Insertando 25 códigos de ejemplo...");

            // Array de códigos de ejemplo
            Codigo[] codigos = {
                // MOVIMIENTO
                new Codigo(null, "Movimiento básico del jugador 2D",
                    "Sistema simple de movimiento para un personaje 2D. El jugador se mueve izquierda/derecha con las flechas del teclado.\n\n**Cómo implementarlo:**\n1. Crea un nodo CharacterBody2D\n2. Agrega un CollisionShape2D hijo (forma cápsula)\n3. Adjunta este script\n4. Presiona F5 para probar",
                    "Movimiento", "Oct 12, 2025", "CodeGodot", 42, 15,
                    "extends CharacterBody2D\n\nconst SPEED = 300.0\n\nfunc _physics_process(delta):\n    var direction = Input.get_axis(\"ui_left\", \"ui_right\")\n    \n    if direction:\n        velocity.x = direction * SPEED\n    else:\n        velocity.x = move_toward(velocity.x, 0, SPEED)\n    \n    move_and_slide()"),

                new Codigo(null, "Salto con gravedad",
                    "Movimiento 2D con saltos realistas. El jugador salta cuando presiona espacio y cae con gravedad física.\n\n**Cómo implementarlo:**\n1. Crea CharacterBody2D con CollisionShape2D\n2. Crea un nodo StaticBody2D debajo como \"suelo\"\n3. Adjunta este script al jugador\n4. Ajusta JUMP_VELOCITY si necesitas saltos más altos",
                    "Movimiento", "Oct 15, 2025", "CodeGodot", 58, 28,
                    "extends CharacterBody2D\n\nconst SPEED = 300.0\nconst JUMP_VELOCITY = -400.0\n\nvar gravity = ProjectSettings.get_setting(\"physics/2d/default_gravity\")\n\nfunc _physics_process(delta):\n    # Aplicar gravedad\n    if not is_on_floor():\n        velocity.y += gravity * delta\n\n    # Saltar si estamos en el suelo\n    if Input.is_action_just_pressed(\"ui_accept\") and is_on_floor():\n        velocity.y = JUMP_VELOCITY\n\n    # Movimiento horizontal\n    var direction = Input.get_axis(\"ui_left\", \"ui_right\")\n    if direction:\n        velocity.x = direction * SPEED\n    else:\n        velocity.x = move_toward(velocity.x, 0, SPEED)\n\n    move_and_slide()"),

                new Codigo(null, "Dash/Salto corto rápido",
                    "Mechanic de dash: el jugador se mueve rápidamente en una dirección al presionar una tecla. Perfecto para evasión o ataque ágil.\n\n**Cómo implementarlo:**\n1. Configura el script en tu CharacterBody2D\n2. Cambia DASH_KEY si deseas otra tecla (ej: \"shift\")\n3. Ajusta DASH_SPEED y DASH_DURATION según necesites",
                    "Movimiento", "Oct 18, 2025", "Elena Wong", 72, 42,
                    "extends CharacterBody2D\n\nconst SPEED = 300.0\nconst DASH_SPEED = 600.0\nconst DASH_DURATION = 0.3\n\nvar is_dashing = false\nvar dash_timer = 0.0\n\nfunc _physics_process(delta):\n    if Input.is_action_just_pressed(\"ui_cancel\") and not is_dashing:\n        is_dashing = true\n        dash_timer = DASH_DURATION\n    \n    var direction = Input.get_axis(\"ui_left\", \"ui_right\")\n    var current_speed = DASH_SPEED if is_dashing else SPEED\n    \n    if direction:\n        velocity.x = direction * current_speed\n    else:\n        velocity.x = move_toward(velocity.x, 0, current_speed)\n    \n    if is_dashing:\n        dash_timer -= delta\n        if dash_timer <= 0:\n            is_dashing = false\n    \n    move_and_slide()"),

                new Codigo(null, "Rotación hacia el ratón",
                    "El personaje se rota automáticamente para mirar hacia la posición del ratón. Útil para juegos de disparos o RPG.\n\n**Cómo implementarlo:**\n1. Adjunta a un Node2D o CharacterBody2D\n2. El script gira el nodo hacia el cursor automáticamente cada frame\n3. Personaliza la rotación con el código",
                    "Movimiento", "Oct 20, 2025", "Marcus Johnson", 51, 19,
                    "extends Node2D\n\nfunc _process(delta):\n    var mouse_pos = get_global_mouse_position()\n    var direction = (mouse_pos - global_position).normalized()\n    var angle = direction.angle()\n    rotation = angle"),

                // INTERACCIÓN
                new Codigo(null, "Sistema de interacción simple",
                    "Permite al jugador interactuar con objetos al presionar E. Ideal para recoger items, hablar con NPCs, abrir puertas.\n\n**Cómo implementarlo:**\n1. Crea un Area2D para el objeto interactuable\n2. Agrega un CollisionShape2D hijo\n3. Adjunta este script al Area2D\n4. Conecta la señal \"interacted\" a lo que desees hacer",
                    "Interacción", "Oct 22, 2025", "CodeGodot", 64, 31,
                    "extends Area2D\n\nsignal interacted\n\nfunc _ready():\n    area_entered.connect(_on_area_entered)\n    area_exited.connect(_on_area_exited)"),

                new Codigo(null, "Recoger items",
                    "Script para items que el jugador puede recoger. Suma puntos o agrega al inventario cuando se toca.\n\n**Cómo implementarlo:**\n1. Crea un Area2D para el item\n2. Adjunta este script\n3. Personaliza POINTS_VALUE según el item\n4. Conecta a tu sistema de puntuación",
                    "Interacción", "Oct 25, 2025", "Sarah Chen", 48, 22,
                    "extends Area2D\n\nconst POINTS_VALUE = 10\n\nfunc _ready():\n    area_entered.connect(_on_area_entered)"),

                new Codigo(null, "Diálogo simple con NPC",
                    "Sistema básico de diálogo. Muestra un panel de texto cuando hablas con un NPC. Presiona espacio para avanzar.\n\n**Cómo implementarlo:**\n1. Crea un CanvasLayer para el panel\n2. Agrega un Label hijo para el texto\n3. Adjunta este script al NPC (Area2D)\n4. Rellena el array \"dialogues\" con tu texto",
                    "Interacción", "Oct 28, 2025", "CodeGodot", 76, 45,
                    "extends Area2D\n\nvar dialogues = []\nvar current_dialogue = 0"),

                // COMBATE
                new Codigo(null, "Sistema de vida/salud básico",
                    "Gestión de vida del jugador y de enemigos. Toma daño, cura, muere cuando la salud llega a 0.\n\n**Cómo implementarlo:**\n1. Agrega este script a tu personaje\n2. Conecta signals de daño desde enemigos\n3. Crea una UI que muestre la barra de vida",
                    "Combate", "Nov 1, 2025", "CodeGodot", 85, 52,
                    "extends Node2D\n\nsignal health_changed\nvar max_health = 100\nvar current_health = 100"),

                new Codigo(null, "Disparar proyectiles",
                    "Script que permite disparar balas en la dirección del ratón. Perfecto para juegos de disparos.\n\n**Cómo implementarlo:**\n1. Crea una escena \"Bullet\" con Area2D y CollisionShape2D\n2. Agrega este script al jugador\n3. Guarda la escena de bala en res://Bullet.tscn\n4. Presiona clic izquierdo para disparar",
                    "Combate", "Nov 4, 2025", "Lucas Patel", 92, 61,
                    "extends Node2D\n\nvar bullet_scene = preload(\"res://Bullet.tscn\")\nconst BULLET_SPEED = 500.0"),

                new Codigo(null, "Detección de colisión para daño",
                    "Causa daño cuando una bala o ataque golpea a un enemigo. Usa áreas de colisión para detectar impactos.\n\n**Cómo implementarlo:**\n1. Adjunta a un proyectil o arma\n2. Conecta la señal area_entered\n3. Verifica si el área golpeó un enemigo\n4. Llama a take_damage() del enemigo",
                    "Combate", "Nov 7, 2025", "CodeGodot", 77, 38,
                    "extends Area2D\n\nvar damage = 10\nvar hit_targets = []"),

                // ANIMACIONES
                new Codigo(null, "Animación de caminar",
                    "Anima el personaje mientras camina con AnimatedSprite2D. La animación cambia según la dirección.\n\n**Cómo implementarlo:**\n1. Crea un AnimatedSprite2D hijo en tu personaje\n2. Crea animaciones \"walk_left\" y \"walk_right\" en el AnimatedSprite\n3. Adjunta este script\n4. Personaliza la velocidad de animación si necesitas",
                    "Animación", "Nov 10, 2025", "CodeGodot", 69, 33,
                    "extends CharacterBody2D\n\nconst SPEED = 300.0\n@onready var animated_sprite = $AnimatedSprite2D"),

                new Codigo(null, "Tween - Animación suave",
                    "Usa Tween para animar objetos suavemente (mover, rotar, escalar). Muy útil para UI y efectos visuales.\n\n**Cómo implementarlo:**\n1. Adjunta a cualquier Node2D o Control\n2. El script anima el movimiento automáticamente\n3. Personaliza duration y el destino (target_pos)",
                    "Animación", "Nov 13, 2025", "Sophie Laurent", 81, 47,
                    "extends Node2D\n\nvar target_pos = Vector2(400, 300)\nvar duration = 2.0"),

                // FÍSICA
                new Codigo(null, "Rebote elástico",
                    "El objeto rebota cuando golpea paredes o suelo. Perfecto para bolas, piedras o efectos de explosión.\n\n**Cómo implementarlo:**\n1. Crea un RigidBody2D\n2. Agrega un CollisionShape2D\n3. Adjunta este script\n4. Personaliza ELASTICITY para más/menos rebote",
                    "Física", "Nov 16, 2025", "CodeGodot", 56, 24,
                    "extends RigidBody2D\n\nconst ELASTICITY = 0.7\nconst FRICTION = 0.1"),

                new Codigo(null, "Atracción/Repulsión",
                    "Los objetos se atraen o repelen uno del otro. Crea órbitas, campos de fuerza o imanes.\n\n**Cómo implementarlo:**\n1. Coloca dos Node2D en la escena\n2. Adjunta este script a uno de ellos\n3. Asigna la referencia al otro en el Inspector\n4. Personaliza FORCE_STRENGTH para intensidad",
                    "Física", "Nov 19, 2025", "David Kim", 43, 18,
                    "extends Node2D\n\n@export var target: Node2D\n@export var FORCE_STRENGTH = 500.0"),

                // ENTRADA
                new Codigo(null, "Soporte para gamepad/joystick",
                    "Detecta entrada de gamepad (botones, joysticks) para controles completos de consola.\n\n**Cómo implementarlo:**\n1. Adjunta a tu jugador\n2. Conecta un gamepad al PC\n3. Los botones ya estarán mapeados en Input Map\n4. Personaliza según tus necesidades",
                    "Entrada", "Nov 22, 2025", "CodeGodot", 67, 36,
                    "extends CharacterBody2D\n\nconst SPEED = 300.0"),

                new Codigo(null, "Entrada de ratón - Click para mover",
                    "El personaje se mueve al lugar donde haces clic. Estilo point-and-click clásico.\n\n**Cómo implementarlo:**\n1. Adjunta a tu personaje (CharacterBody2D)\n2. Haz clic en la escena para mover\n3. El personaje caminará hacia el punto automáticamente",
                    "Entrada", "Nov 25, 2025", "Anna Rodriguez", 74, 41,
                    "extends CharacterBody2D\n\nconst SPEED = 200.0\nvar target_position = null"),

                // UI
                new Codigo(null, "Barra de vida visual",
                    "UI que muestra la salud como barra. Se actualiza cuando tomas daño o te curas.\n\n**Cómo implementarlo:**\n1. Crea un Control con un ColorRect hijo para la barra\n2. Adjunta este script\n3. Conecta con tu sistema de vida\n4. Personaliza los colores (rojo para bajo, verde para alto)",
                    "UI", "Nov 28, 2025", "CodeGodot", 89, 58,
                    "extends Control\n\n@onready var health_bar = $ColorRect"),

                new Codigo(null, "Menú pausa simple",
                    "Pausa el juego cuando presionas ESC. Muestra un panel con botones de reanudar y salir.\n\n**Cómo implementarlo:**\n1. Crea un CanvasLayer para el menú\n2. Agrega botones y etiquetas\n3. Adjunta este script\n4. El juego se pausa automáticamente",
                    "UI", "Dic 1, 2025", "James Wilson", 102, 73,
                    "extends CanvasLayer\n\nvar is_paused = false"),

                new Codigo(null, "Sistema de puntuación",
                    "Gestiona puntos del jugador. Suma puntos, muestra en pantalla, guarda máximo.\n\n**Cómo implementarlo:**\n1. Crea un Label en la UI\n2. Adjunta este script a un Node de control general\n3. Conecta signals para sumar puntos\n4. El Label se actualiza automáticamente",
                    "UI", "Dic 4, 2025", "CodeGodot", 71, 44,
                    "extends Node\n\nvar current_score = 0\nvar high_score = 0"),

                new Codigo(null, "Fade in/out de pantalla",
                    "Oscurece la pantalla gradualmente (fade out) o la aclara (fade in). Perfecto para transiciones entre niveles.\n\n**Cómo implementarlo:**\n1. Crea un CanvasLayer con un ColorRect negro\n2. Adjunta este script\n3. Llama a fade_out() o fade_in() desde otros scripts\n4. Usa para transiciones de niveles",
                    "UI", "Dic 7, 2025", "Rosa García", 58, 31,
                    "extends CanvasLayer\n\n@onready var fade_rect = $ColorRect"),

                // SEÑALES
                new Codigo(null, "Sistema de señales básico",
                    "Usa señales para comunicar eventos entre nodos sin acoplamiento. Un botón emite una señal, otros nodos la escuchan.\n\n**Cómo implementarlo:**\n1. Define una signal en el script del emisor\n2. Emite la señal con emit_signal()\n3. Otros scripts se conectan con .connect()\n4. Responden cuando se emite",
                    "Señales", "Dic 10, 2025", "CodeGodot", 64, 37,
                    "# Script del emisor (Botón)\nextends Button\n\nsignal button_pressed"),

                new Codigo(null, "Eventos globales con Autoload",
                    "Crea un singleton global que emite eventos para toda la aplicación. Útil para cambios de nivel, música, efectos globales.\n\n**Cómo implementarlo:**\n1. Crea un script nuevo (EventManager.gd)\n2. Añádelo como Autoload en Project Settings\n3. Otros scripts emiten con EventManager.level_complete.emit()\n4. Conecta desde cualquier parte del juego",
                    "Señales", "Dic 13, 2025", "Kevin Lee", 77, 49,
                    "# EventManager.gd - Añadido como Autoload\nextends Node\n\nsignal level_complete"),

                // EFECTOS
                new Codigo(null, "Partículas de explosión",
                    "Crea un efecto de partículas cuando algo explota. Usa GPUParticles2D para rendimiento óptimo.\n\n**Cómo implementarlo:**\n1. Crea un nodo GPUParticles2D\n2. Configura el material y las propiedades de emisión\n3. Adjunta este script\n4. Llama a explode() cuando necesites el efecto",
                    "Efectos", "Dic 16, 2025", "CodeGodot", 73, 45,
                    "extends GPUParticles2D\n\nfunc _ready():\n    emitting = false"),

                new Codigo(null, "Sonidos de efectos (SFX)",
                    "Reproduce sonidos cuando ocurren eventos (salto, golpe, recoger item). Maneja volumen y reproducción.\n\n**Cómo implementarlo:**\n1. Crea un nodo AudioStreamPlayer\n2. Carga un archivo .ogg o .mp3\n3. Adjunta este script\n4. Llama a play_sound() desde eventos",
                    "Efectos", "Dic 19, 2025", "Lisa Park", 82, 52,
                    "extends AudioStreamPlayer\n\n@export var jump_sound: AudioStream"),

                new Codigo(null, "Cámara que sigue al jugador",
                    "La cámara sigue al personaje del jugador en todo momento. Mantiene el juego centrado y visible.\n\n**Cómo implementarlo:**\n1. Crea un nodo Camera2D hijo del jugador\n2. O adjunta este script a una Camera2D\n3. Personaliza SMOOTHING para velocidad de seguimiento\n4. Agrega límites de cámara si necesitas",
                    "Cámara", "Dic 22, 2025", "CodeGodot", 96, 61,
                    "extends Camera2D\n\n@export var target: Node2D\n@export var smoothing = 0.1")
            };

            // Guardar todos los códigos
            for (Codigo codigo : codigos) {
                codigoRepository.save(codigo);
            }

            log.info("✅ Se insertaron correctamente {} códigos.", codigos.length);
            log.info("📍 Puedes consultar los códigos en: http://localhost:8080/api/codigos");
        };
    }
}
