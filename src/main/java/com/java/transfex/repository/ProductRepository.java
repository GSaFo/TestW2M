package com.java.transfex.repository;

import com.java.transfex.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la tabla producto
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);

    void deleteById(Integer id);
}
