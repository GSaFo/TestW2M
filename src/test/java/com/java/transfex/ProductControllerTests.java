package com.java.transfex;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.java.transfex.adapter.OptionalTypeAdapter;
import com.java.transfex.model.PaginatedResponse;
import com.java.transfex.model.Product;
import com.java.transfex.model.ProductDTO;
import com.java.transfex.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/schema.sql"})
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Gson gson = new GsonBuilder().registerTypeAdapter(Optional.class, new OptionalTypeAdapter()).excludeFieldsWithoutExposeAnnotation().create();

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        // Crear un objeto Product de prueba
        product = new Product(1, "Galletas", "Galletas de chocolate", 2.20, "Alimentacion");
        productDTO = new ProductDTO("Galletas", "Galletas de chocolate", 2.20, "Alimentacion");
    }

    @Test
    void testGetAllProducts() throws Exception {
        // Simular que el servicio devuelve una lista de productos paginada
        PaginatedResponse<Product> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setContent(List.of(product));
        when(productService.getAllProducts(0, 10)).thenReturn(paginatedResponse);

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/products")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Galletas"));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        // Simular que el servicio no encuentra el producto
        when(productService.getProductById(999)).thenReturn(Optional.empty());

        // Realizar la solicitud GET con un ID que no existe
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", 999))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateProduct() throws Exception {
        // Simular la creación de un producto
        when(productService.create(Mockito.any(Product.class))).thenReturn(product);

        // Realizar la solicitud POST
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galletas"))
                .andExpect(jsonPath("$.description").value("Galletas de chocolate"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        // Simular la actualización de un producto
        when(productService.update(eq(1), Mockito.any(Product.class))).thenReturn(product);

        // Realizar la solicitud PUT
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(productDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galletas"))
                .andExpect(jsonPath("$.description").value("Galletas de chocolate"));
    }

    @Test
    void testUpdateProductNotFound() throws Exception {
        // Simular que el producto no se encuentra
        when(productService.update(eq(999), Mockito.any(Product.class))).thenReturn(null);

        // Realizar la solicitud PUT con un ID que no existe
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(productDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("The product has not been found."));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).delete(1);

        mockMvc.perform(delete("/api/products/{id}", 1))
                .andExpect(status().isNoContent());
    }
}