package library.services;

import java.util.List;

import library.dto.BookDto;
import library.dto.res.PagewiseRes;

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
