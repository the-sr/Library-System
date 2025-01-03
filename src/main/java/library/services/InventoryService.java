package library.services;

import java.util.List;

import library.dto.BookDto;
import library.dto.res.AvailabilityRes;
import library.dto.res.BookHistoryRes;

public interface InventoryService {
    AvailabilityRes getAvailability(long id);

    List<BookDto> getBooksWithLowStock();

    BookHistoryRes getBookHistory(long id);
}
