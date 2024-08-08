package com.example.demo.services.mappers;

import com.example.demo.models.Book;
import com.example.demo.payloads.req.BookReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {BookDetailsMapper.class})
public interface BookDetailsMapper extends MapperClass<Book, BookReq> {

    Book toEntity(BookReq bookReq);

    BookReq toDto(Book book);

    default String trimString(String title){
        return title == null ? "" : title.trim();
    }
}