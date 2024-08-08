package com.example.demo.services;

import com.example.demo.payloads.req.BookReq;
import com.example.demo.payloads.res.BookRes;
import com.example.demo.payloads.res.PagewiseRes;

import java.util.List;

public interface BookService {

    String saveBook(BookReq book);

    List<BookRes> getAllBooks();

    PagewiseRes<BookRes> getAllBooksPagewise(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    BookRes getBookById(Long id);

    List<BookRes> getBookByTitle(String title);

    void updateBook(BookReq book);

    void deleteBookById(Long id);
}
