package com.example.demo.services.mappers;

public interface MapperClass<E,D>{

    E toEntity(D d);

    D toDto(E e);
}
