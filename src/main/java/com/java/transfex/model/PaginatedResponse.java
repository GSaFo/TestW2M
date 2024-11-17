package com.java.transfex.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Respuesta paginada")
@Data
public class PaginatedResponse<T> {

    @Schema(description = "Contenido de la página actual")
    private List<T> content;

    @Schema(description = "Número total de elementos")
    private int totalElements;

    @Schema(description = "Número total de páginas")
    private int totalPages;

    @Schema(description = "Número de la página actual")
    private int currentPage;

    @Schema(description = "Tamaño de la página")
    private int pageSize;
}