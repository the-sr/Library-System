package library.services;

import library.dto.UserBookDto;

import java.util.List;

public interface UserBookService {

    String borrowRequest(Long bookId);

    String returnRequest(Long bookId);

    UserBookDto getById(Long userBookId);

    List<UserBookDto> getAll(Long userId, String requestType, Boolean isActive);

    String handelBorrowRequest(Long userBookId);

    String handelReturnRequest(Long userBookId);
}
