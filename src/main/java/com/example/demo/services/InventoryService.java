package com.example.demo.services;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.res.AvailabilityRes;
import com.example.demo.dto.res.BookHistoryRes;

import java.util.List;

public interface InventoryService {
    AvailabilityRes getAvailability(long id);
    List<BookDto> getBooksWithLowStock();
    BookHistoryRes getBookHistory(long id);
}
