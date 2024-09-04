package com.example.demo.services.mappers;

import com.example.demo.models.Book;
import com.example.demo.dto.req.BookReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper extends MapperClass<Book, BookReq,Void> {

    Book dtoToEntity(BookReq bookReq);

    BookReq toBookReq(Book book);

}
