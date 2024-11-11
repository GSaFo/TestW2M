package com.java.testw2m.service;

import com.java.testw2m.model.Ship;
import com.java.testw2m.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio principal para el manejo de los objetos Prices
 */
@Service
public class ShipService {
    @Autowired
    private ShipRepository shipRepository;

    // Consultar todas las naves con paginación
    public Page<Ship> getAllShips(int page, int size) {
        return shipRepository.findAll(PageRequest.of(page, size));
    }

    // Consultar nave por id
    @Cacheable(value = "ships", key = "#id")
    public Optional<Ship> getShipById(Integer id) {
        return shipRepository.findById(id);
    }

    // Consultar naves por nombre que contengan un valor
    public List<Ship> getShipsByName(String name) {
        return shipRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Metodo que crea una nave
     *
     * @return Una lista de naves o una lista vacia en caso de no encontrar naves que cumplan los parametros
     */
    public Ship create(Ship ship) {
        return shipRepository.save(ship);
    }

    /**
     * Metodo que updatea una nave
     *
     * @return Una lista de naves o una lista vacia en caso de no encontrar naves que cumplan los parametros
     */
    public Ship update(Ship ship) {
        Optional<Ship> savedShip = shipRepository.findById(ship.getId());

        if (savedShip.isEmpty()) {
            return null;
        }

        return shipRepository.save(ship);
    }

    /**
     * Metodo que elimina una nave
     *
     * @param id El id de la nave a eliminar
     */
    @CacheEvict(value = "naves", key = "#id")
    public void delete(Integer id) {
        shipRepository.deleteById(id);
    }
}
