package library.services.mappers;

import library.dto.AddressDto;
import library.models.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends MapperClass<Address, AddressDto, Void>{

    Address dtoToEntity(AddressDto addressDto);

    AddressDto entityToDto(Address address);
}
