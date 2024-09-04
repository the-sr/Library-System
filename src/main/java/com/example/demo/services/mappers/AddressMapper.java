package com.example.demo.services.mappers;

import com.example.demo.dto.AddressDto;
import com.example.demo.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper extends MapperClass<Address, AddressDto, Void> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user",ignore = true)
    Address dtoToEntity(AddressDto addressDto);

    AddressDto entityToDto(Address address);

}
