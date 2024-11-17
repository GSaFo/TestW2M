package com.java.testw2m.controller;

import com.java.testw2m.model.User;
import com.java.testw2m.service.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user") //localhost:8080/user
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Recibe un usuario y la inserta en la bbdd
     *
     * @param user El usuario a insertar
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @PostMapping("createUser")
    @ApiResponse(
            description = "Crear nuevo usuario",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = User.class,
                            example = "{ \"username\": \"gabriel123\", \"password\": \"123asd123\" }"
                    )
            )
    )
    public ResponseEntity<String> create(@RequestBody User user) {
        // Comprobamos parametros de entrada
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Parameter data is not valid.");
        }

        return userService.create(user) != null ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }
}
