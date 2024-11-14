package com.java.testw2m.controller;

import com.java.testw2m.model.LoginRequest;
import com.java.testw2m.model.User;
import com.java.testw2m.service.UserService;
import com.java.testw2m.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/spaceShip") //localhost:8080/ship
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.getByUserName(loginRequest);

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String token = JwtUtils.generateToken(loginRequest.getUsername());

        return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
    }
}
