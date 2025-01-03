package library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import library.dto.BookDto;
import library.dto.UserBookDto;
import library.dto.res.AvailabilityRes;
import library.dto.res.BookHistoryRes;
import library.services.BookService;
import library.services.InventoryService;
import library.services.UserBookService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private static final int LOW_STOCK = 5;

    private final BookService bookService;
    private final UserBookService userBookService;

    @Override
    public AvailabilityRes getAvailability(long id) {
        BookDto bookDto = bookService.getBookById(id);
        return AvailabilityRes.builder()
                .book(bookDto)
                .isAvailable(bookDto.getBookCount() > 0)
                .build();
    }

    @Override
    public List<BookDto> getBooksWithLowStock() {
        List<BookDto> res = new ArrayList<>();
        bookService.getAllBooks().forEach(book -> {
            if (book.getBookCount() <= LOW_STOCK) {
                res.add(book);
            }
        });
        return res;
    }

    @Override
    public BookHistoryRes getBookHistory(long id) {
        BookDto bookDto = bookService.getBookById(id);
        List<UserBookDto> userBookDtoList = null;
        return null;
    }
}
