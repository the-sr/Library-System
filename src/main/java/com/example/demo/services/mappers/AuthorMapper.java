package com.example.demo.services.mappers;

import com.example.demo.dto.AuthorDto;
import com.example.demo.models.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends MapperClass<Author, AuthorDto,Void>{

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "books",ignore = true)
    Author dtoToEntity(AuthorDto authorDto);

    AuthorDto entityToDto(Author author);

}
