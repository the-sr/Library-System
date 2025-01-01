package com.example.demo.services.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.UserBookDto;
import com.example.demo.dto.res.AvailabilityRes;
import com.example.demo.dto.res.BookHistoryRes;
import com.example.demo.services.BookService;
import com.example.demo.services.InventoryService;
import com.example.demo.services.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private static final int LOW_STOCK =5;

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
        List<BookDto> res=new ArrayList<>();
        bookService.getAllBooks().forEach(book->{
            if(book.getBookCount()<=LOW_STOCK){
                res.add(book);
            }
        });
        return res;
    }

    @Override
    public BookHistoryRes getBookHistory(long id) {
        BookDto bookDto = bookService.getBookById(id);
        List<UserBookDto> userBookDtoList= null;
        return null;
    }
}
