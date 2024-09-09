package com.example.demo.services.mappers;

import com.example.demo.dto.BookDto;
import com.example.demo.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper extends MapperClass<Book, BookDto,Void> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genre",ignore = true)
    Book dtoToEntity(BookDto bookDto);

    BookDto entityToDto(Book book);

}
