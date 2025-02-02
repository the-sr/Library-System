package library.services;

import library.dto.BookDto;
import library.dto.UserDto;
import library.dto.res.PagewiseRes;

import java.util.List;

public interface BookService {
    String add(BookDto req);

    BookDto findById(long id);

    List<BookDto> findByTitle(String title);

    List<BookDto> findByAuthor(String author);

    List<BookDto> findByGenre(String genre);

    List<BookDto> getAllBooks();

    PagewiseRes<BookDto> getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    String removeById(long id);

    UserDto updateById(BookDto req);
}
