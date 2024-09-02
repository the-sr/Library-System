package com.example.demo.services.mappers;

import com.example.demo.models.Book;
import com.example.demo.payloads.req.BookReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper extends MapperClass<Book, BookReq> {

    Book toEntity(BookReq bookReq);

    BookReq toBookReq(Book book);

}
