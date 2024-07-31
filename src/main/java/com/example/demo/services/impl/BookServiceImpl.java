package com.example.demo.services.impl;

import com.example.demo.exception.CustomException;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Genre;
import com.example.demo.payloads.req.AuthorReq;
import com.example.demo.payloads.req.BookReq;
import com.example.demo.payloads.res.AuthorRes;
import com.example.demo.payloads.res.BookRes;
import com.example.demo.payloads.res.GenreRes;
import com.example.demo.payloads.res.PagewiseRes;
import com.example.demo.projection.BookProjection;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.repository.BookRepo;
import com.example.demo.repository.GenreRepo;
import com.example.demo.services.AuthorService;
import com.example.demo.services.BookService;
import com.example.demo.services.GenreService;
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
    private final GenreRepo genreRepo;
    private final GenreService genreService;

    @Override
    public String saveBook(BookReq bookReq) {
        //Saving Author
        Set<Author> authors = new HashSet<>();
        bookReq.getAuthors().forEach(author->{
            Optional<Author> a= authorRepo.findByEmail(author.getEmail());
            if(a.isPresent()) authors.add(a.get());
            else authors.add(authorService.addAuthor(author));
        });
        //Saving Genre
        List<Genre> genres=new ArrayList<>();
        bookReq.getGenre().forEach(genre->{
            Optional<Genre> g=genreRepo.findByName(genre.getName());
            if(g.isPresent()) genres.add(g.get());
            else genres.add(genreService.addGenre(genre));
        });
        //Saving Book
        Book book= Book.builder()
                .id(bookRepo.findNextId())
                .isbn(bookReq.getIsbn())
                .title(bookReq.getTitle())
                .edition(bookReq.getEdition())
                .publisher(bookReq.getPublisher())
                .bookCount(bookReq.getBookCount())
                .authors(authors)
                .genre(genres)
                .build();
        bookRepo.save(book);
        return "Book saved.";
    }

    @Override
    public List<BookRes> getAllBooks() {
//        List<Book> books=bookRepo.findAll();
        return TransferObject.convert(bookRepo.findAll(),BookRes.class);
    }

    @Override
    public PagewiseRes<BookRes> getAllBooksPagewise(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sort;
        if(sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Book> books=bookRepo.findAll(pageable);

        List<BookRes> bookResList=new ArrayList<>();
        books.forEach(book->{
            //BookRes bookRes=TransferObject.convert(book,BookRes.class);
            bookResList.add(TransferObject.convert(book, BookRes.class));
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

    @Override
    public BookRes getBookById(Long id) {
        return TransferObject.convert(bookRepo.findById(id),BookRes.class);
    }


    @Override
    public List<BookRes> getBookByTitle(String title) {
        List<Book> books=bookRepo.findByTitle(title);
        return null;
    }

    @Override
    public void updateBook(BookReq book) {

    }

    @Override
    public void deleteBookById(Long id) {

    }
}
