package com.example.demo.services.mappers;

import com.example.demo.dto.UserBookDto;
import com.example.demo.models.UserBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserBookMapper extends MapperClass<UserBook, UserBookDto,Void> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    UserBook dtoToEntity(UserBookDto dto);

    @Mapping(target = "user",ignore = true)
    @Mapping(target = "book",ignore = true)
    UserBookDto entityToDto(UserBook userBook);

}
