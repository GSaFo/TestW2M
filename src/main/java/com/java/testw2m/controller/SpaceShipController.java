package com.java.testw2m.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.testw2m.helper.SpaceShipHelper;
import com.java.testw2m.model.PaginatedResponse;
import com.java.testw2m.model.SpaceShip;
import com.java.testw2m.model.SpaceShipDTO;
import com.java.testw2m.service.SpaceShipService;
import com.java.testw2m.util.Utils;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/spaceShip") //localhost:8080/ship
public class SpaceShipController {

    @Autowired
    private SpaceShipService spaceShipService;
    private Gson gson = new GsonBuilder().create();

    @GetMapping("getAllShips")
    public ResponseEntity<PaginatedResponse<SpaceShip>> getAllShips(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        PaginatedResponse<SpaceShip> ships = spaceShipService.getAllShips(page, size);
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    /**
     * Recibe un id de nave y devuelve el resultado
     *
     * @param id Id de la nave
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @GetMapping("getShipById/{id}")
    @ApiResponse(
            description = "Obtener una nave espacial por su ID",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = SpaceShip.class,
                            example = "{ \"id\": 1, \"name\": \"X-Wing\", \"description\": \"Star Wars.\" }"
                    )
            )
    )
    public ResponseEntity<String> getById(@PathVariable Integer id) {
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
    @GetMapping("getShipByName/{name}")
    @ApiResponse(
            description = "Obtener naves espaciales por nombre",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = List.class,
                            type = "array",
                            description = "Lista de naves espaciales",
                            example = "[{ \"id\": 1, \"name\": \"Ishimura\", \"description\": \"Dead Space.\" }, { \"id\": 2, \"name\": \"X-Wing\", \"description\": \"Star Wars.\" }]"
                    )
            )
    )
    public ResponseEntity<String> getByName(@PathVariable String name) {
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
     * @param spaceShipDTO La nave a insertar
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @PostMapping("createShip")
    @ApiResponse(
            description = "Crear una nueva nave espacial",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = SpaceShip.class,
                            example = "{ \"id\": 1, \"name\": \"X-Wing\", \"description\": \"Star Wars.\" }"
                    )
            )
    )
    public ResponseEntity<String> create(@RequestBody SpaceShipDTO spaceShipDTO) {
        // Comprobamos parametros de entrada
        if (spaceShipDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Parameter data is not valid.");
        }

        SpaceShip spaceShip = SpaceShipHelper.transformDTO(spaceShipDTO);

        SpaceShip inserted = spaceShipService.create(spaceShip);

        return ResponseEntity.ok(gson.toJson(inserted));
    }

    /**
     * Recibe una nave e intenta actualizarla
     *
     * @param spaceShipDTO La nave a actualizar
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @PutMapping("updateShip")
    @ApiResponse(
            description = "Actualizar una nave espacial existente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = SpaceShip.class,
                            example = "{ \"id\": 1, \"name\": \"X-Wing\", \"description\": \"Star Wars.\" }"
                    )
            )
    )
    public ResponseEntity<String> update(@RequestBody SpaceShipDTO spaceShipDTO) {
        // Comprobamos parametros de entrada
        if (spaceShipDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Parameter data is not valid.");
        }

        SpaceShip spaceShip = SpaceShipHelper.transformDTO(spaceShipDTO);

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
    @DeleteMapping("deleteShip/{id}")
    @ApiResponse(
            description = "Eliminar una nave espacial existente mediante ID"
    )
    public ResponseEntity<String> update(@PathVariable Integer id) {
        spaceShipService.delete(id);

        return ResponseEntity.ok().build();
    }
}
