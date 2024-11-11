package com.java.testw2m.repository;

import com.java.testw2m.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la tabla prices
 */
@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {
    List<Ship> findByNameContainingIgnoreCase(String name);

    void deleteById(Integer id);
}
