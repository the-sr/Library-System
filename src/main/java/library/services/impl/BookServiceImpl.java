package library.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import library.dto.BookDto;
import library.dto.res.PagewiseRes;
import library.exception.CustomException;
import library.models.Author;
import library.models.Book;
import library.models.Genre;
import library.repository.AuthorRepo;
import library.repository.BookRepo;
import library.repository.GenreRepo;
import library.services.AuthorService;
import library.services.BookService;
import library.services.GenreService;
import library.services.mappers.BookMapper;

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
    private final BookMapper bookMapper;

    @Override
    public String saveUpdateBook(BookDto bookDto) {
        // Save
        if (bookDto.getId() == null) {
            CompletableFuture<Set<Author>> authorFuture = CompletableFuture.supplyAsync(() -> {
                return saveUpdateAuthor(bookDto);
            }).exceptionally(ex -> {
                throw new CustomException("Error while saving book: " + ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            });
            CompletableFuture<List<Genre>> genreFuture = CompletableFuture.supplyAsync(() -> {
                return saveUpdateGenre(bookDto);
            }).exceptionally(ex -> {
                throw new CustomException("Error while saving genre: " + ex.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            });
            try {
                Set<Author> authors = authorFuture.get();
                List<Genre> genres = genreFuture.get();
                Book book = bookMapper.dtoToEntity(bookDto);
                book.setId(bookRepo.findNextId());
                book.setAuthors(authors);
                book.setGenre(genres);
                bookRepo.save(book);
                return "Book saved.";
            } catch (Exception e) {
                throw new CustomException("Error Adding Book", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Update
        Book book = bookRepo.findById(bookDto.getId())
                .orElseThrow(() -> new CustomException("Book Not Found", HttpStatus.NOT_FOUND));
        CompletableFuture<Set<Author>> authorFuture = CompletableFuture.supplyAsync(() -> {
            return saveUpdateAuthor(bookDto);
        }).exceptionally(ex -> {
            throw new CustomException("Error while updating author: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        });
        CompletableFuture<List<Genre>> genreFuture = CompletableFuture.supplyAsync(() -> {
            return saveUpdateGenre(bookDto);
        }).exceptionally(ex -> {
            throw new CustomException("Error while updating genre: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        });
        try {
            Set<Author> authors = authorFuture.get();
            List<Genre> genres = genreFuture.get();
            book.setAuthors(authors);
            book.setGenre(genres);
            book.setIsbn(bookDto.getIsbn());
            book.setTitle(bookDto.getTitle());
            book.setEdition(bookDto.getEdition());
            book.setPublisher(bookDto.getPublisher());
            book.setBookCount(book.getBookCount());
            bookRepo.save(book);
            return "Book updated.";
        } catch (Exception e) {
            throw new CustomException("Error Updating Book", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<BookDto> bookDtoList = new ArrayList<>();
        bookRepo.findAll().forEach(book -> bookDtoList.add(bookMapper.entityToDto(book)));
        return bookDtoList;
    }

    @Override
    public PagewiseRes<BookDto> getAllBooksPagewise(Integer pageNumber, Integer pageSize, String sortBy,
            String sortDirection) {
        Sort sort;
        if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Book> books = bookRepo.findAll(pageable);
        List<BookDto> bookDtoList = new ArrayList<>();
        books.forEach(book -> {
            bookDtoList.add(bookMapper.entityToDto(book));
        });
        PagewiseRes<BookDto> pagewiseRes = new PagewiseRes<>();
        pagewiseRes.setRes(bookDtoList);
        pagewiseRes.setCurrentPage(books.getNumber());
        pagewiseRes.setTotalPages(books.getTotalPages());
        pagewiseRes.setTotalElements(books.getTotalElements());
        pagewiseRes.setPageSize(books.getSize());
        pagewiseRes.setLast(books.isLast());
        return pagewiseRes;
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new CustomException("Book Not Found", HttpStatus.NOT_FOUND));
        return bookMapper.entityToDto(book);
    }

    @Override
    public List<BookDto> getBookByTitle(String title) {
        List<BookDto> bookDtoList = new ArrayList<>();
        bookRepo.findByTitle(title).forEach(book -> bookDtoList.add(bookMapper.entityToDto(book)));
        return bookDtoList;
    }

    @Override
    public List<BookDto> getBookByAuthor(String author) {
        List<BookDto> bookDtoList = new ArrayList<>();
        bookRepo.findByAuthor(author).forEach(book -> bookDtoList.add(bookMapper.entityToDto(book)));
        return bookDtoList;
    }

    @Override
    public BookDto getBookByISBN(String isbn) {
        return bookMapper.entityToDto(bookRepo.findByISBN(isbn)
                .orElseThrow(() -> new CustomException("Book Not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public String deleteBookById(Long id) {
        bookRepo.deleteById(id);
        return "Book Deleted Successfully";
    }

    private Set<Author> saveUpdateAuthor(BookDto bookDto) {
        if (bookDto.getAuthors() != null) {
            Set<Author> authors = new HashSet<>();
            bookDto.getAuthors().forEach(author -> {
                Optional<Author> a = authorRepo.findByEmail(author.getEmail());
                if (a.isPresent())
                    authors.add(a.get());
                else
                    authors.add(authorService.addAuthor(author));
            });
            return authors;
        }
        return authorRepo.findByBookId(bookDto.getId());
    }

    private List<Genre> saveUpdateGenre(BookDto bookDto) {
        if (bookDto.getGenre() != null) {
            List<Genre> genres = new ArrayList<>();
            bookDto.getGenre().forEach(genre -> {
                Optional<Genre> g = genreRepo.findByName(genre.getName());
                if (g.isPresent())
                    genres.add(g.get());
                else
                    genres.add(genreService.addGenre(genre));
            });
            return genres;
        }
        return genreRepo.findByBookId(bookDto.getId());
    }
}
