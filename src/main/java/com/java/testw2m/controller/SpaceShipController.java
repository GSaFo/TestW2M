package com.java.testw2m.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.testw2m.model.SpaceShip;
import com.java.testw2m.service.SpaceShipService;
import com.java.testw2m.util.Utils;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador principal de las llamadas a la api
 */
@RestController
@RequestMapping("/ship") //localhost:8080/ship
public class SpaceShipController {

    @Autowired
    private SpaceShipService spaceShipService;
    private Gson gson = new GsonBuilder().create();

    @GetMapping("getAllShips")
    public ResponseEntity<Page<SpaceShip>> getAllShips(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Page<SpaceShip> ships = spaceShipService.getAllShips(page, size);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    /**
     * Recibe un id de nave y devuelve el resultado
     *
     * @param id Id de la nave
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @GetMapping("getShipById")
    public ResponseEntity<String> getById(@RequestParam Integer id) {
        Optional<SpaceShip> ship = spaceShipService.getShipById(id);

        // Comprobamos si nos ha llegado un prices veridico o uno vacio. En caso de estar vacio es que no lo ha encontrado
        if (ship.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gson.toJson(ship));
    }

    /**
     * Recibe un nombre parcial o completo de nave devuelve una lista de coincidencias
     *
     * @param name Nombre o parte del nombre de la nave
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @GetMapping("getShipByName")
    public ResponseEntity<String> getByName(@RequestParam String name) {
        // Comprobamos parametros de entrada
        if (StringUtils.isEmpty(name)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Parameter data is not valid.");
        }

        List<SpaceShip> spaceShips = spaceShipService.getShipsByName(name);

        // Comprobamos si nos ha llegado un prices veridico o uno vacio. En caso de estar vacio es que no lo ha encontrado
        if (Utils.isEmpty(spaceShips)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gson.toJson(spaceShips));
    }

    /**
     * Recibe una nave y la inserta en la bbdd
     *
     * @param spaceShip La nave a insertar
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @PostMapping("createShip")
    public ResponseEntity<String> create(@RequestBody SpaceShip spaceShip) {
        // Comprobamos parametros de entrada
        if (spaceShip == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Parameter data is not valid.");
        }

        SpaceShip inserted = spaceShipService.create(spaceShip);

        return ResponseEntity.ok(gson.toJson(inserted));
    }

    /**
     * Recibe una nave e intenta actualizarla
     *
     * @param spaceShip La nave a actualizar
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @PutMapping("updateShip")
    public ResponseEntity<String> update(@RequestBody SpaceShip spaceShip) {
        // Comprobamos parametros de entrada
        if (spaceShip == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Parameter data is not valid.");
        }
        SpaceShip updated = spaceShipService.update(spaceShip);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The ship has not been found.");
        }

        return ResponseEntity.ok(gson.toJson(updated));
    }

    /**
     * Recibe un id de nave y lo elimina
     *
     * @param id id de la nave a eliminar
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @DeleteMapping("deleteShip")
    public ResponseEntity<String> update(@RequestBody Integer id) {
        spaceShipService.delete(id);

        return ResponseEntity.ok().build();
    }
}
