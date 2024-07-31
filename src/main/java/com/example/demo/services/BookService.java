package com.example.demo.services;

import com.example.demo.payloads.req.BookReq;
import com.example.demo.payloads.res.BookRes;
import com.example.demo.payloads.res.PagewiseRes;

import java.util.List;

public interface BookService {
    BookRes saveBook(BookReq book);
    BookRes getBookById(Long id);
    List<BookRes> getAllBooks();
    List<BookRes> getBookByTitle(String title);
    void updateBook(BookReq book);
    void deleteBookById(Long id);
    PagewiseRes<BookRes> getAllBooksPagewise(Integer pageNuber, Integer pageSize,String sortBy,String sortDirection);
}
