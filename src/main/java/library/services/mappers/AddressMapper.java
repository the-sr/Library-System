package library.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import library.dto.AddressDto;
import library.models.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper extends MapperClass<Address, AddressDto, Void> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address dtoToEntity(AddressDto addressDto);

    AddressDto entityToDto(Address address);

}
