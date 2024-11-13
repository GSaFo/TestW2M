package com.java.testw2m.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SpaceShipDTO implements Serializable {

    private String name;

    private String description;
}
