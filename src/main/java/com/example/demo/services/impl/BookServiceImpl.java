package com.example.demo.services.impl;

import com.example.demo.exception.CustomException;
import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Genre;
import com.example.demo.payloads.req.BookReq;
import com.example.demo.payloads.res.BookRes;
import com.example.demo.payloads.res.PagewiseRes;
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
import java.util.concurrent.CompletableFuture;

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

        /*
         * CompletableFuture allows for asynchronous programming by enabling tasks to run concurrently.
         *
         * - `supplyAsync(Supplier<T> supplier)`: Executes a task asynchronously and returns a CompletableFuture.
         * - `runAsync(Runnable runnable)`: Executes a task asynchronously that does not return a result.
         * - `thenApply(Function<T, U> fn)`: Transforms the result of the previous task.
         * - `thenAccept(Consumer<T> action)`: Consumes the result of the previous task without transforming it.
         * - `exceptionally(Function<Throwable, T> fn)`: Handles exceptions from the task.
         * - `allOf(CompletableFuture<?>... cfs)`: Completes when all given futures complete.
         * - `anyOf(CompletableFuture<?>... cfs)`: Completes when any of the given futures complete.
         *
         * It helps in managing asynchronous tasks and combining results efficiently.
         */

        //Saving Author
        CompletableFuture<Set<Author>> authorFuture = CompletableFuture.supplyAsync(() -> {
            Set<Author> authors = new HashSet<>();
            bookReq.getAuthors().forEach(author -> {
                Optional<Author> a = authorRepo.findByEmail(author.getEmail());
                if (a.isPresent()) authors.add(a.get());
                else authors.add(authorService.addAuthor(author));
            });
            return authors;
        }).exceptionally(ex -> {
            throw new CustomException("Error while saving book: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        });

        //Saving Genre
        CompletableFuture<List<Genre>> genreFuture = CompletableFuture.supplyAsync(() -> {
            List<Genre> genres = new ArrayList<>();
            bookReq.getGenre().forEach(genre -> {
                Optional<Genre> g = genreRepo.findByName(genre.getName());
                if (g.isPresent()) genres.add(g.get());
                else genres.add(genreService.addGenre(genre));
            });
            return genres;
        }).exceptionally(ex -> {
            throw new CustomException("Error while saving genre: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        });

        try {
            Set<Author> authors = authorFuture.get();
            List<Genre> genres = genreFuture.get();
            //Saving Book
            Book book = Book.builder()
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
        } catch (Exception e) {
            throw new CustomException("Error Adding Book", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<BookRes> getAllBooks() {
//        List<Book> books=bookRepo.findAll();
        return TransferObject.convert(bookRepo.findAll(), BookRes.class);
    }

    @Override
    public PagewiseRes<BookRes> getAllBooksPagewise(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {

        Sort sort;
        if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Book> books = bookRepo.findAll(pageable);

        List<BookRes> bookResList = new ArrayList<>();
        books.forEach(book -> {
            bookResList.add(TransferObject.convert(book, BookRes.class));
        });

        PagewiseRes<BookRes> pagewiseRes = new PagewiseRes<>();
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
        return TransferObject.convert(bookRepo.findById(id), BookRes.class);
    }


    @Override
    public List<BookRes> getBookByTitle(String title) {
        List<Book> books = bookRepo.findByTitle(title);
        return null;
    }

    @Override
    public void updateBook(BookReq book) {

    }

    @Override
    public void deleteBookById(Long id) {

    }
}
