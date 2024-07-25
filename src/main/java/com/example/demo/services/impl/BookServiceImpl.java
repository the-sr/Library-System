package com.example.demo.services.impl;

import com.example.demo.exception.CustomException;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.payloads.req.BookReq;
import com.example.demo.payloads.res.BookRes;
import com.example.demo.repository.BookRepo;
import com.example.demo.services.AuthorService;
import com.example.demo.services.BookService;
import com.example.demo.utils.TransferObject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final AuthorService authorService;

    @Override
    public BookRes saveBook(BookReq bookReq) {
        if(bookRepo.existsByIsbn(bookReq.getIsbn()))
            throw new CustomException("Book with ISBN already exists", HttpStatus.CONFLICT);
        Book book=Book.builder()
                .id(bookRepo.findNextId())
                .isbn(bookReq.getIsbn())
                .title(bookReq.getTitle())
                .edition(bookReq.getEdition())
                .publisher(bookReq.getPublisher())
                .bookCount(bookReq.getBookCount())
                .build();
        bookRepo.save(book);
        bookReq.getAuthors().forEach(author->{
            authorService.addAuthor(author, book.getId());
        });
        return getBookById(book.getId());
    }

    @Override
    public BookRes getBookById(Long id) {
        return null;
    }

    @Override
    public List<BookRes> getAllBooks() {
        return null;
    }

    @Override
    public List<BookRes> getBookByTitle(String title) {
        return null;
    }

    @Override
    public void updateBook(BookReq book) {

    }

    @Override
    public void deleteBookById(Long id) {

    }
}
