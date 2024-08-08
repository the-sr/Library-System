package com.example.demo.services.mappers;

import com.example.demo.models.Author;
import com.example.demo.payloads.req.AuthorReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {AddressDetailsMapper.class})
public interface AuthorDetailsMapper extends MapperClass<Author, AuthorReq> {

    Author toEntity(AuthorReq authorReq);

    AuthorReq toDto(Author author);
}
