package library.services;

import library.dto.BookDto;
import library.dto.UserDto;
import library.dto.res.PagewiseRes;

import java.util.List;
import java.util.Set;

public interface BookService {
    String add(BookDto req);

    BookDto findById(Long id);

    List<BookDto> findByTitle(String title);

    Set<BookDto> findByAuthor(String author);

    Set<BookDto> findByGenre(String genre);

    List<BookDto> getAllBooks();

    PagewiseRes<BookDto> getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);

    String removeById(Long id);

    BookDto updateById(BookDto req);
}
