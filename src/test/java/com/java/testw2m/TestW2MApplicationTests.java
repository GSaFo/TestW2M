package com.java.testw2m;

import com.java.testw2m.model.SpaceShip;
import com.java.testw2m.service.SpaceShipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/schema.sql"})
class TestW2MApplicationTests {

    @Autowired
    private SpaceShipService spaceShipService;  // Asegúrate de que el servicio esté inyectado correctamente

    @Test
    public void testCreateShip() {
        SpaceShip spaceShip = new SpaceShip();
        spaceShip.setName("xwing");
        spaceShip.setDescription("starwars");

        // Llamada al método que debería guardar la nave
        SpaceShip savedSpaceShip = spaceShipService.create(spaceShip);

        // Verifica que la nave no sea null y que el ID se haya generado
        assertNotNull(savedSpaceShip);
        assertNotNull(savedSpaceShip.getId());
        assertEquals("xwing", savedSpaceShip.getName());
        assertEquals("starwars", savedSpaceShip.getDescription());
    }

    @Test
    public void testGetShipById() {
        // Crear y guardar una nave
        SpaceShip spaceShip = new SpaceShip();
        spaceShip.setName("tie fighter");
        spaceShip.setDescription("prueba");

        SpaceShip savedSpaceShip = spaceShipService.create(spaceShip);
        Integer shipId = savedSpaceShip.getId();  // Obtener el ID de la nave recién guardada

        // Obtener la nave por ID
        Optional<SpaceShip> retrievedShip = spaceShipService.getShipById(shipId);

        SpaceShip realRetrievedSpaceShip = retrievedShip.orElse(null);

        assertNotNull(retrievedShip);
        assertEquals(shipId, realRetrievedSpaceShip.getId());
        assertEquals("tie fighter", realRetrievedSpaceShip.getName());
        assertEquals("prueba", realRetrievedSpaceShip.getDescription());
    }

    @Test
    public void testGetAllShipsPaged() {
        // Crear varias naves
        for (int i = 0; i < 15; i++) {
            SpaceShip spaceShip = new SpaceShip();
            spaceShip.setName("Ship " + i);
            spaceShip.setDescription("Description " + i);
            spaceShipService.create(spaceShip);
        }

        // Consultar todas las naves con paginación
        Page<SpaceShip> page = spaceShipService.getAllShips(0, 5);

        assertNotNull(page);
        assertEquals(5, page.getContent().size());  // Debería retornar 5 naves por página
        assertTrue(page.getTotalElements() > 0);  // Debe haber al menos algunas naves guardadas
    }

    @Test
    public void testSearchShipsByName() {
        // Crear algunas naves
        SpaceShip spaceShip1 = new SpaceShip();
        spaceShip1.setName("x-wing");
        spaceShip1.setDescription("a");
        spaceShipService.create(spaceShip1);

        SpaceShip spaceShip2 = new SpaceShip();
        spaceShip2.setName("y-wing");
        spaceShip2.setDescription("b");
        spaceShipService.create(spaceShip2);

        SpaceShip spaceShip3 = new SpaceShip();
        spaceShip3.setName("z-wing");
        spaceShip3.setDescription("c");
        spaceShipService.create(spaceShip3);

        // Buscar naves que contengan "Wing" en su nombre
        List<SpaceShip> spaceShips = spaceShipService.getShipsByName("wing");

        assertNotNull(spaceShips);
        assertEquals(3, spaceShips.size());  // Deberíamos obtener las 3 naves
        assertTrue(spaceShips.stream().allMatch(spaceShip -> spaceShip.getName().contains("wing")));
    }

    @Test
    public void testUpdateShip() {
        // Crear y guardar una nave
        SpaceShip spaceShip = new SpaceShip();
        spaceShip.setName("tie fighter");
        spaceShip.setDescription("asdasd");
        SpaceShip savedSpaceShip = spaceShipService.create(spaceShip);
        Integer shipId = savedSpaceShip.getId();

        // Actualizar la nave
        savedSpaceShip.setName("tie interceptor");
        savedSpaceShip.setDescription("prueba update");

        SpaceShip updatedSpaceShip = spaceShipService.update(savedSpaceShip);

        // Verificar que la nave se haya actualizado correctamente
        assertNotNull(updatedSpaceShip);
        assertEquals(shipId, updatedSpaceShip.getId());  // El ID debe mantenerse igual
        assertEquals("tie interceptor", updatedSpaceShip.getName());
        assertEquals("prueba update", updatedSpaceShip.getDescription());
    }

    @Test
    void testCreateAndRetrieveShip() {
        // Crear un nuevo objeto Ship
        SpaceShip spaceShip = new SpaceShip();
        spaceShip.setName("x-wing");
        spaceShip.setDescription("testing");

        // Guardar el objeto Ship en la base de datos
        SpaceShip createdSpaceShip = spaceShipService.create(spaceShip);

        // Verificar que el objeto se ha guardado correctamente
        assertNotNull(createdSpaceShip.getId());
        assertEquals("x-wing", createdSpaceShip.getName());
        assertEquals("testing", createdSpaceShip.getDescription());

        // Recuperar el objeto de la base de datos
        SpaceShip retrievedSpaceShip = spaceShipService.getShipById(createdSpaceShip.getId()).orElse(null);

        // Verificar que el objeto recuperado es el mismo
        assertNotNull(retrievedSpaceShip);
        assertEquals(createdSpaceShip.getId(), retrievedSpaceShip.getId());
        assertEquals(createdSpaceShip.getName(), retrievedSpaceShip.getName());
    }
}

