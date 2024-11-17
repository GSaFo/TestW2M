package com.java.transfex.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    @NotNull(message = "Name cannot be null")
    @Schema(description = "Nombre del producto", example = "Galletas")
    private String name;

    @Schema(description = "Breve descripcion del producto", example = "Descripcion dummy")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Schema(description = "Precio del producto", example = "5.43")
    private Double price;

    @NotNull(message = "Category cannot be null")
    @Schema(description = "Categoria del producto", example = "Alimentacion")
    private String category;
}
