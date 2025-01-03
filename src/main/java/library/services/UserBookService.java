package library.services;

import java.util.List;

import library.dto.UserBookDto;

public interface UserBookService {
    String borrowBook(long bookId);

    String returnBook(long bookId);

    List<UserBookDto> getUserBookByUserId(long userId);

    UserBookDto getUserBookByBookId(long bookId);

    UserBookDto getAllUserBooks();
}
