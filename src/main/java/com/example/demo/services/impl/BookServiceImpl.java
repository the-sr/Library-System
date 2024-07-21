package com.example.demo.services.impl;

import com.example.demo.exception.CustomException;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.payloads.req.BookReq;
import com.example.demo.payloads.res.BookRes;
import com.example.demo.repository.BookRepo;
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

    @Override
    public void saveBook(BookReq bookReq) {
        Book book =Book.builder()
                .id(bookRepo.findNextId())
                .isbn(bookReq.getIsbn())
                .title(bookReq.getTitle())
                .edition(bookReq.getEdition())
                .authors(TransferObject.convert(bookReq.getAuthors(), Author.class))
                .publisher(bookReq.getPublisher())
                .bookCount(bookReq.getBookCount())
                .build();
        bookRepo.save(book);

    }

    @Override
    public BookRes getBookById(Long id) {
        Optional<Book> book = bookRepo.findById(id);
        if(book.isEmpty()) throw new CustomException("Book Not Found", HttpStatus.NOT_FOUND);
        return TransferObject.convert(book.get(), BookRes.class);
    }

    @Override
    public List<BookRes> getAllBooks() {
        List<Book> books = bookRepo.findAll();
        return TransferObject.convert(books, BookRes.class);
    }

    @Override
    public List<BookRes> getBookByAuthor(String author) {
        List<Book> books=bookRepo.findByAuthor(author);
        return TransferObject.convert(books, BookRes.class);
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
