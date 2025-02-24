package com.mrcooper.userservice.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntityConverter<E, D> {
    private final ModelMapper modelMapper;

    public EntityConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public D mapEntityToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
}
