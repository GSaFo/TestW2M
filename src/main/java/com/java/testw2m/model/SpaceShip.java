package com.java.testw2m.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "spaceship")
public class SpaceShip implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la nave espacial", example = "1")
    private Integer id;

    @Column(name = "name")
    @Schema(description = "Nombre de la nave espacial", example = "Ishimura")
    private String name;

    @Column(name = "description")
    @Schema(description = "Breve descripcion de la nave espacial", example = "Dead Space")
    private String description;


}
