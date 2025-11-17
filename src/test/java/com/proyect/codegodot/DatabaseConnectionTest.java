package com.proyect.codegodot;

import com.proyect.codegodot.Model.Codigo;
import com.proyect.codegodot.Repository.CodigoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test de integración para verificar la conexión con la base de datos MySQL
 * y las operaciones básicas del repositorio
 */
@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private CodigoRepository codigoRepository;

    @Test
    void testDatabaseConnection() {
        // Verificar que el repositorio está inyectado correctamente
        assertNotNull(codigoRepository, "El repositorio no debería ser null");
        
        System.out.println("✅ Conexión a la base de datos exitosa");
        System.out.println("✅ CodigoRepository inyectado correctamente");
    }

    @Test
    void testFindAllCodigos() {
        // Verificar que podemos consultar la tabla (aunque esté vacía)
        List<Codigo> codigos = codigoRepository.findAll();
        
        assertNotNull(codigos, "La lista de códigos no debería ser null");
        
        System.out.println("✅ Consulta a la tabla 'codigos' exitosa");
        System.out.println("📊 Cantidad de registros encontrados: " + codigos.size());
        
        // Mostrar los códigos si existen
        if (!codigos.isEmpty()) {
            System.out.println("\n📋 Códigos en la base de datos:");
            codigos.forEach(codigo -> 
                System.out.println("  - ID: " + codigo.getId() + " | Título: " + codigo.getTitulo())
            );
        } else {
            System.out.println("ℹ️  La tabla 'codigos' está vacía (esto es normal en una BD nueva)");
        }
    }

    @Test
    void testCreateAndDeleteCodigo() {
        // Crear un código de prueba
        Codigo codigoPrueba = new Codigo();
        codigoPrueba.setTitulo("TEST: Código de prueba");
        codigoPrueba.setDescripcion("Este es un código de prueba para verificar la conexión");
        codigoPrueba.setCodigo("func _ready():\n    print('Prueba exitosa')");
        
        // Guardar el código
        Codigo codigoGuardado = codigoRepository.save(codigoPrueba);
        
        assertNotNull(codigoGuardado.getId(), "El ID no debería ser null después de guardar");
        assertEquals("TEST: Código de prueba", codigoGuardado.getTitulo());
        
        System.out.println("✅ Código de prueba creado con ID: " + codigoGuardado.getId());
        
        // Verificar que existe en la BD
        assertTrue(codigoRepository.existsById(codigoGuardado.getId()), 
                   "El código debería existir en la BD");
        
        System.out.println("✅ Código verificado en la base de datos");
        
        // Eliminar el código de prueba
        codigoRepository.deleteById(codigoGuardado.getId());
        
        // Verificar que fue eliminado
        assertFalse(codigoRepository.existsById(codigoGuardado.getId()), 
                    "El código no debería existir después de eliminarlo");
        
        System.out.println("✅ Código de prueba eliminado correctamente");
        System.out.println("✅ Test CRUD básico completado exitosamente");
    }
}
