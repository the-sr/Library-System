package library.services.impl;

import library.dto.AuthorDto;
import library.dto.BookDto;
import library.dto.UserDto;
import library.dto.res.PagewiseRes;
import library.exception.CustomException;
import library.models.Book;
import library.models.BookAuthor;
import library.models.BookGenre;
import library.repository.BookAuthorRepo;
import library.repository.BookGenreRepo;
import library.repository.BookRepo;
import library.services.AuthorService;
import library.services.BookService;
import library.services.GenreService;
import library.services.mappers.AuthorMapper;
import library.services.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final BookAuthorRepo bookAuthorRepo;
    private final GenreService genreService;
    private final BookGenreRepo bookGenreRepo;
    private final AuthorMapper authorMapper;

    @Override
    public String add(BookDto req) {
        Book book = bookRepo.save(bookMapper.dtoToEntity(req));
        if (req.getAuthors() != null && !req.getAuthors().isEmpty()) {
            req.getAuthors().parallelStream().forEach(author -> {
                author = authorService.add(author);
                BookAuthor bookAuthor = BookAuthor.builder()
                        .bookId(book.getId())
                        .authorId(author.getId())
                        .build();
                bookAuthorRepo.save(bookAuthor);
            });
        }
        if (req.getGenre() != null && !req.getGenre().isEmpty()) {
            req.getGenre().parallelStream().forEach(genre -> {
                genre = genreService.add(genre);
                BookGenre bookGenre = BookGenre.builder()
                        .bookId(book.getId())
                        .genreId(genre.getId())
                        .build();
                bookGenreRepo.save(bookGenre);
            });
        }
        return "Book added successfully";
    }

    @Override
    public BookDto findById(long id) {
        Book book=bookRepo.findById(id).orElseThrow(()->new CustomException("Book not found", HttpStatus.NOT_FOUND));
        BookDto res=bookMapper.entityToDto(book);
        List<BookAuthor> bookAuthorList=bookAuthorRepo.findByBookId(id);
        List<AuthorDto> authorDtoList=new ArrayList<>();
        bookAuthorList.parallelStream().forEach(bookAuthor -> {
            authorDtoList.add(authorMapper.entityToDto(bookAuthor.getAuthor()));
        });
        res.setAuthors(authorDtoList);
        return res;
    }

    @Override
    public List<BookDto> findByTitle(String title) {
        return List.of();
    }

    @Override
    public List<BookDto> findByAuthor(String author) {
        return List.of();
    }

    @Override
    public List<BookDto> findByGenre(String genre) {
        return List.of();
    }

    @Override
    public List<BookDto> getAllBooks() {
        return List.of();
    }

    @Override
    public PagewiseRes<BookDto> getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        return null;
    }

    @Override
    public String removeById(long id) {
        return "";
    }

    @Override
    public UserDto updateById(BookDto req) {
        return null;
    }
}
