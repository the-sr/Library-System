package com.example.demo.services.mappers;

public interface MapperClass<E, D> {
    D toDto(E e);

    E toEntity(D dto);
}
