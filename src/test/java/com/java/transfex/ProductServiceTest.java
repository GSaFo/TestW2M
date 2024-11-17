package com.java.transfex;

import com.java.transfex.model.PaginatedResponse;
import com.java.transfex.model.Product;
import com.java.transfex.repository.ProductRepository;
import com.java.transfex.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product(1, "Galletas", "Galletas de chocolate", 2.20, "Alimentacion");
    }

    @Test
    void testFindProducts_withNameAndCategory() {
        // Given
        String name = "Galletas";
        String category = "Alimentacion";
        when(productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category))
                .thenReturn(List.of(product));

        // When
        List<Product> products = productService.findProducts(name, category);

        // Then
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Galletas", products.get(0).getName());
        assertEquals("Alimentacion", products.get(0).getCategory());
    }

    @Test
    void testGetAllProducts() {
        // Given
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(product), pageRequest, 1);
        when(productRepository.findAll(pageRequest)).thenReturn(page);

        // When
        PaginatedResponse<Product> paginatedResponse = productService.getAllProducts(0, 10);

        // Then
        assertNotNull(paginatedResponse);
        assertEquals(1, paginatedResponse.getContent().size());
        assertEquals("Galletas", paginatedResponse.getContent().get(0).getName());
    }

    @Test
    void testGetProductById_found() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When
        Optional<Product> foundProduct = productService.getProductById(1);

        // Then
        assertTrue(foundProduct.isPresent());
        assertEquals("Galletas", foundProduct.get().getName());
    }

    @Test
    void testGetProductById_notFound() {
        // Given
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Optional<Product> foundProduct = productService.getProductById(999);

        // Then
        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testCreate() {
        // Given
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        // When
        Product createdProduct = productService.create(product);

        // Then
        assertNotNull(createdProduct);
        assertEquals("Galletas", createdProduct.getName());
    }

    @Test
    void testUpdate_found() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        // When
        Product updatedProduct = productService.update(1, product);

        // Then
        assertNotNull(updatedProduct);
        assertEquals("Galletas", updatedProduct.getName());
    }

    @Test
    void testUpdate_notFound() {
        // Given
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // When
        Product updatedProduct = productService.update(999, product);

        // Then
        assertNull(updatedProduct);
    }

    @Test
    void testDelete() {
        // Given
        doNothing().when(productRepository).deleteById(1);

        // When
        productService.delete(1);

        // Then
        verify(productRepository, times(1)).deleteById(1);
    }
}