package com.java.testw2m.helper;

import com.java.testw2m.mapper.MapperConfig;
import com.java.testw2m.model.SpaceShip;
import com.java.testw2m.model.SpaceShipDTO;

public class SpaceShipHelper {

    private static MapperConfig mapperConfig = new MapperConfig();

    /**
     * Metodo para convertir un objeto SpaceShipDTO a un SpaceShip
     *
     * @param spaceShipDTO Un objeto spaceship
     * @return Un objeto simplificado
     */
    public static SpaceShip transformDTO(SpaceShipDTO spaceShipDTO) {
        return mapperConfig.map(spaceShipDTO, SpaceShip.class);
    }
}
