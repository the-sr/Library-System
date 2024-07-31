package com.example.demo.services.impl;

import com.example.demo.exception.CustomException;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.payloads.req.AuthorReq;
import com.example.demo.payloads.req.BookReq;
import com.example.demo.payloads.res.BookRes;
import com.example.demo.payloads.res.PagewiseRes;
import com.example.demo.projection.BookProjection;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.repository.BookRepo;
import com.example.demo.services.AuthorService;
import com.example.demo.services.BookService;
import com.example.demo.utils.TransferObject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final AuthorService authorService;
    private final AuthorRepo authorRepo;

    @Override
    public BookRes saveBook(BookReq bookReq) {
        Set<AuthorReq> authorReqsList=bookReq.getAuthors();
        Set<Author> authors = new HashSet<>();
        authorReqsList.forEach(author->{
            if(!authorRepo.existsByEmail(author.getEmail())) {
                authors.add(authorRepo.findByEmail(author.getEmail()));
            }else authors.add(authorService.addAuthor(author));
        });
        Book book= Book.builder()
                .id(bookRepo.findNextId())
                .isbn(bookReq.getIsbn())
                .title(bookReq.getTitle())
                .edition(bookReq.getEdition())
                .publisher(bookReq.getPublisher())
                .bookCount(bookReq.getBookCount())
                .build();
        bookRepo.save(book);
        authors.forEach(author->{
            book.getAuthors().add(author);
        });
        bookRepo.save(book);
        return null;
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

    @Override
    public PagewiseRes<BookRes> getAllBooksPagewise(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort=null;
        if(sortDirection.equalsIgnoreCase("asc"))
            sort = Sort.by(sortBy).ascending();
        else if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<BookProjection> books=bookRepo.findAllBookPagewise(pageable);

        List<BookRes> bookResList=new ArrayList<>();
        books.forEach(book->{
            BookRes bookRes=TransferObject.convert(book,BookRes.class);
            //retrieve author details using bookId and add it to bookRes
            bookResList.add(bookRes);
        });

        PagewiseRes<BookRes> pagewiseRes=new PagewiseRes<>();
        pagewiseRes.setRes(bookResList);
        pagewiseRes.setCurrentPage(books.getNumber());
        pagewiseRes.setTotalPages(books.getTotalPages());
        pagewiseRes.setTotalElements(books.getTotalElements());
        pagewiseRes.setPageSize(books.getSize());
        pagewiseRes.setLast(books.isLast());

        return pagewiseRes;
    }
}
