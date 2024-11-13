package com.java.testw2m.controller;

import com.java.testw2m.model.LoginRequest;
import com.java.testw2m.util.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spaceShip") //localhost:8080/ship
public class LoginController {
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Generar un token JWT sin validar las credenciales
        String token = JwtUtils.generateToken(loginRequest.getUsername());

        return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
    }
}
