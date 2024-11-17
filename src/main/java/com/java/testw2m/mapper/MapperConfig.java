package com.java.testw2m.mapper;

import org.modelmapper.ModelMapper;

public class MapperConfig {
    private final ModelMapper modelMapper = new ModelMapper();

    public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }
}