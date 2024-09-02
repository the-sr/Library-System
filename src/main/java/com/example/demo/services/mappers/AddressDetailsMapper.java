package com.example.demo.services.mappers;

import com.example.demo.models.Address;
import com.example.demo.payloads.req.AddressReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {AddressDetailsMapper.class})
public interface AddressDetailsMapper extends MapperClass<Address, AddressReq> {

    @Mappings({
            @Mapping(target = "id",ignore = true,),
            @Mapping(target = "user", ignore = true)
    })
    Address toEntity(AddressReq addressReq);

    AddressReq toDto(Address address);

}
