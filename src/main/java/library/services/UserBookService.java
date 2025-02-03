package library.services;

import library.dto.UserBookDto;

import java.util.List;

public interface UserBookService {

    String borrowBook(Long bookId);

    String returnBook(Long bookId);

    UserBookDto getById(Long userBookId);

    List<UserBookDto> getByUserId(Long userId);

    List<UserBookDto> getAll();
}
