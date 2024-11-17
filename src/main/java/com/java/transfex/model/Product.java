package com.java.transfex.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del producto", example = "1")
    private Integer id;

    @Column(name = "name")
    @NotNull(message = "Name cannot be null")
    @Schema(description = "Nombre del producto", example = "Galletas")
    private String name;

    @Column(name = "description")
    @Schema(description = "Breve descripcion del producto", example = "Descripcion dummy")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Price cannot be null")
    @Schema(description = "Precio del producto", example = "5.43")
    private Double price;

    @Column(name = "category")
    @NotNull(message = "Category cannot be null")
    @Schema(description = "Categoria del producto", example = "Alimentacion")
    private String category;


}
