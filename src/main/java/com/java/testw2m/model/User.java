package com.java.testw2m.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del usuario", example = "1")
    private Integer id;

    @Column(name = "name")
    @Schema(description = "Nombre del usuario espacial", example = "gabriel123")
    @NonNull
    private String username;

    @Column(name = "password")
    @Schema(description = "Password del usuario", example = "123123")
    @NonNull
    private String password;
}
