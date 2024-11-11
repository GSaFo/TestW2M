package com.java.testw2m;

import com.java.testw2m.model.Ship;
import com.java.testw2m.service.ShipService;
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
    private ShipService shipService;  // Asegúrate de que el servicio esté inyectado correctamente

    @Test
    public void testCreateShip() {
        Ship ship = new Ship();
        ship.setName("xwing");
        ship.setDescription("starwars");

        // Llamada al método que debería guardar la nave
        Ship savedShip = shipService.create(ship);

        // Verifica que la nave no sea null y que el ID se haya generado
        assertNotNull(savedShip);
        assertNotNull(savedShip.getId());
        assertEquals("xwing", savedShip.getName());
        assertEquals("starwars", savedShip.getDescription());
    }

    @Test
    public void testGetShipById() {
        // Crear y guardar una nave
        Ship ship = new Ship();
        ship.setName("TIE Fighter");
        ship.setDescription("Imperial Starfighter");

        Ship savedShip = shipService.create(ship);
        Integer shipId = savedShip.getId();  // Obtener el ID de la nave recién guardada

        // Obtener la nave por ID
        Optional<Ship> retrievedShip = shipService.getShipById(shipId);

        Ship realRetrievedShip = retrievedShip.orElse(null);

        assertNotNull(retrievedShip);
        assertEquals(shipId, realRetrievedShip.getId());
        assertEquals("TIE Fighter", realRetrievedShip.getName());
        assertEquals("Imperial Starfighter", realRetrievedShip.getDescription());
    }

    @Test
    public void testGetAllShipsPaged() {
        // Crear varias naves
        for (int i = 0; i < 15; i++) {
            Ship ship = new Ship();
            ship.setName("Ship " + i);
            ship.setDescription("Description " + i);
            shipService.create(ship);
        }

        // Consultar todas las naves con paginación
        Page<Ship> page = shipService.getAllShips(0, 5);

        assertNotNull(page);
        assertEquals(5, page.getContent().size());  // Debería retornar 5 naves por página
        assertTrue(page.getTotalElements() > 0);  // Debe haber al menos algunas naves guardadas
    }

    @Test
    public void testSearchShipsByName() {
        // Crear algunas naves
        Ship ship1 = new Ship();
        ship1.setName("x-wing");
        ship1.setDescription("a");
        shipService.create(ship1);

        Ship ship2 = new Ship();
        ship2.setName("y-wing");
        ship2.setDescription("b");
        shipService.create(ship2);

        Ship ship3 = new Ship();
        ship3.setName("z-wing");
        ship3.setDescription("c");
        shipService.create(ship3);

        // Buscar naves que contengan "Wing" en su nombre
        List<Ship> ships = shipService.getShipsByName("wing");

        assertNotNull(ships);
        assertEquals(3, ships.size());  // Deberíamos obtener las 3 naves
        assertTrue(ships.stream().allMatch(ship -> ship.getName().contains("wing")));
    }

    @Test
    public void testUpdateShip() {
        // Crear y guardar una nave
        Ship ship = new Ship();
        ship.setName("tie fighter");
        ship.setDescription("asdasd");
        Ship savedShip = shipService.create(ship);
        Integer shipId = savedShip.getId();

        // Actualizar la nave
        savedShip.setName("TIE Interceptor");
        savedShip.setDescription("Faster than the standard TIE Fighter");

        Ship updatedShip = shipService.update(savedShip);

        // Verificar que la nave se haya actualizado correctamente
        assertNotNull(updatedShip);
        assertEquals(shipId, updatedShip.getId());  // El ID debe mantenerse igual
        assertEquals("TIE Interceptor", updatedShip.getName());
        assertEquals("Faster than the standard TIE Fighter", updatedShip.getDescription());
    }
}
