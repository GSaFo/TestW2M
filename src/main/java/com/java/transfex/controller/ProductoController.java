package com.java.transfex.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.transfex.mapper.MapperConfig;
import com.java.transfex.model.PaginatedResponse;
import com.java.transfex.model.Product;
import com.java.transfex.model.ProductDTO;
import com.java.transfex.service.ProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controlador principal de las llamadas a la api
 */
@RestController
@RequestMapping("/api") //localhost:8080/api
public class ProductoController {

    @Autowired
    private ProductService productService;
    private MapperConfig mapperConfig = new MapperConfig();
    private Gson gson = new GsonBuilder().create();

    @GetMapping("products")
    public ResponseEntity<PaginatedResponse<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        PaginatedResponse<Product> products = productService.getAllProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping
    public List<Product> findProducts(@RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "category", required = false) String category) {
        return productService.findProducts(name, category);
    }

    /**
     * Recibe un id de un producto y devuelve el resultado
     *
     * @param id Id del producto
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @GetMapping("products/{id}")
    @ApiResponse(
            description = "Obtener producto por su ID",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Product.class,
                            example = "{ \"id\": 1, \"name\": \"Galletas\", \"description\": \"Galletas de chocolate.\", \"price\": \"2.20\", \"category\": \"Alimentacion\" }"
                    ))
    )
    public ResponseEntity<String> getById(@PathVariable Integer id) {
        Optional<Product> product = productService.getProductById(id);

        if (product.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(gson.toJson(product));
    }

    /**
     * Recibe un producto y la inserta en la bbdd
     *
     * @param product El producto a insertar
     * @return Un 200 con un JSON del objeto insertado o en caso de no encontrarlo un 204 vacio.
     */
    @PostMapping("products")
    @ApiResponse(
            description = "Crear un nuevo producto",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Product.class,
                            example = "{ \"id\": 1, \"name\": \"Galletas\", \"description\": \"Galletas de chocolate.\", \"price\": \"2.20\", \"category\": \"Alimentacion\" }"
                    )
            )
    )
    public ResponseEntity<String> create(@RequestBody @Valid ProductDTO product) {
        // Comprobamos parametros de entrada
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Parameter data is not valid.");
        }

        Product productEntity = mapperConfig.map(product, Product.class);

        Product inserted = productService.create(productEntity);

        return ResponseEntity.ok(gson.toJson(inserted));
    }

    /**
     * Recibe un producto e intenta actualizarlo
     *
     * @param product El producto a actualizar
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @PutMapping("products/{id}")
    @ApiResponse(
            description = "Actualizar producto existente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            implementation = Product.class,
                            example = "{ \"id\": 1, \"name\": \"Galletas\", \"description\": \"Galletas de chocolate.\", \"price\": \"2.20\", \"category\": \"Alimentacion\" }"
                    )
            )
    )
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody @Valid ProductDTO product) {
        // Comprobamos parametros de entrada
        if (id == null || product == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Parameter data is not valid.");
        }

        Product productEntity = mapperConfig.map(product, Product.class);

        Product updated = productService.update(id, productEntity);

        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product has not been found.");
        }

        return ResponseEntity.ok(gson.toJson(updated));
    }

    /**
     * Recibe un id de producto y lo elimina
     *
     * @param id id del producto
     * @return Un 200 con un JSON del objeto encontrado o en caso de no encontrarlo un 204 vacio.
     */
    @DeleteMapping("products/{id}")
    @ApiResponse(
            description = "Eliminar producto existente mediante ID"
    )
    public ResponseEntity<String> update(@PathVariable Integer id) {
        productService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
