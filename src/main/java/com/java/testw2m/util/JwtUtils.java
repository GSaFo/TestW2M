package com.java.testw2m.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    private static final String SECRET_KEY = "Secret123";

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)  // No importa el usuario real, pero usamos uno para el token
                .setIssuedAt(new Date())  // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // Expiración del token (1 día)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)  // Firma con la clave secreta
                .compact();
    }

}
