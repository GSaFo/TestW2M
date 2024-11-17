package com.java.transfex.service;

import com.java.transfex.mapper.MapperConfig;
import com.java.transfex.model.PaginatedResponse;
import com.java.transfex.model.Product;
import com.java.transfex.repository.ProductRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio principal para el manejo de los objetos Prices
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    private MapperConfig mapperConfig = new MapperConfig();

    public List<Product> findProducts(String name, String category) {
        if (StringUtils.isEmpty(name)) {
            name = "";
        }
        if (StringUtils.isEmpty(category)) {
            category = "";
        }

        return productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(name, category);
    }

    // Consultar todos los productos con paginación
    public PaginatedResponse<Product> getAllProducts(int page, int size) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        return mapperConfig.map(productPage, PaginatedResponse.class);
    }

    // Consultar producto por id
    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    /**
     * Metodo que crea un producto
     *
     * @return El producto que se ha creado
     */
    public Product create(Product product) {
        return productRepository.save(product);
    }

    /**
     * Metodo que actualiza un producto
     *
     * @return El producto actualizado o null en caso de no existir
     */
    public Product update(Integer id, Product product) {
        Optional<Product> savedProduct = productRepository.findById(id);

        if (savedProduct.isEmpty()) {
            return null;
        }

        return productRepository.save(product);
    }

    /**
     * Metodo que elimina un poroducto
     *
     * @param id El id del producto a eliminar
     */
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}
