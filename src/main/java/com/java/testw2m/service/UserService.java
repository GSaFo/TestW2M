package com.java.testw2m.service;

import com.java.testw2m.model.LoginRequest;
import com.java.testw2m.model.User;
import com.java.testw2m.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * Metodo que crea un usuario nuevo
     *
     * @return El usuario que se ha creado o null en caos de ya existir
     */
    public User create(User request) {
        Optional<User> existingUser = repository.findByUserNameContainingIgnoreCase(request.getUsername());
        if (existingUser.isPresent()) {
            return null;
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(), hashedPassword);
        return repository.save(user);
    }

    /**
     * Metodo que busca un usuario en la bbdd
     *
     * @return El usuario en caso de existir
     */
    public Optional<User> getByUserName(LoginRequest request) {
        return repository.findByUserNameContainingIgnoreCase(request.getUsername());
    }

}