package com.example.demo.services;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.res.PagewiseRes;

import java.util.List;

public interface BookService {

    String saveUpdateBook(BookDto book);

    List<BookDto> getAllBooks();

    PagewiseRes<BookDto> getAllBooksPagewise(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    BookDto getBookById(Long id);

    List<BookDto> getBookByTitle(String title);

    List<BookDto> getBookByAuthor(String author);

    BookDto getBookByISBN(String isbn);

    String deleteBookById(Long id);

}
