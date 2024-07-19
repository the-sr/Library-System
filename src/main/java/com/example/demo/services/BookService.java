package com.example.demo.services;

import com.example.demo.payloads.req.BookReq;

public interface BookService {
    void saveBook(BookReq book);
    void getBookById(Long id);
    void getAllBooks();
    void getBookByAuthor(String author);
    void getBookByTitle(String title);
    void updateBook(BookReq book);
    void deleteBookById(Long id);
}
