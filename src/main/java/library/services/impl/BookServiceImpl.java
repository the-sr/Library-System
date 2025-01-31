package library.services.impl;

import library.dto.BookDto;
import library.dto.UserDto;
import library.dto.res.PagewiseRes;
import library.models.Book;
import library.services.BookService;
import library.services.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    @Override
    public BookDto add(BookDto req) {
        Book book=bookMapper.dtoToEntity(req);

        return null;
    }

    @Override
    public BookDto findById(long id) {
        return null;
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
