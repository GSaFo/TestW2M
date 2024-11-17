package com.java.testw2m.repository;

import com.java.testw2m.model.SpaceShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la tabla prices
 */
@Repository
public interface SpaceShipRepository extends JpaRepository<SpaceShip, Integer> {
    List<SpaceShip> findByNameContainingIgnoreCase(String name);

    void deleteById(Integer id);
}
