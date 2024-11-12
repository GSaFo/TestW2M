package com.java.testw2m.service;

import com.java.testw2m.model.SpaceShip;
import com.java.testw2m.repository.SpaceShipRepository;
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
public class SpaceShipService {
    @Autowired
    private SpaceShipRepository spaceShipRepository;

    // Consultar todas las naves con paginación
    public Page<SpaceShip> getAllShips(int page, int size) {
        return spaceShipRepository.findAll(PageRequest.of(page, size));
    }

    // Consultar nave por id
    @Cacheable(value = "ships", key = "#id")
    public Optional<SpaceShip> getShipById(Integer id) {
        return spaceShipRepository.findById(id);
    }

    // Consultar naves por nombre que contengan un valor
    public List<SpaceShip> getShipsByName(String name) {
        return spaceShipRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Metodo que crea una nave
     *
     * @return Una lista de naves o una lista vacia en caso de no encontrar naves que cumplan los parametros
     */
    public SpaceShip create(SpaceShip spaceShip) {
        return spaceShipRepository.save(spaceShip);
    }

    /**
     * Metodo que updatea una nave
     *
     * @return Una lista de naves o una lista vacia en caso de no encontrar naves que cumplan los parametros
     */
    public SpaceShip update(SpaceShip spaceShip) {
        Optional<SpaceShip> savedShip = spaceShipRepository.findById(spaceShip.getId());

        if (savedShip.isEmpty()) {
            return null;
        }

        return spaceShipRepository.save(spaceShip);
    }

    /**
     * Metodo que elimina una nave
     *
     * @param id El id de la nave a eliminar
     */
    @CacheEvict(value = "naves", key = "#id")
    public void delete(Integer id) {
        spaceShipRepository.deleteById(id);
    }
}
