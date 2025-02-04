package library.services.impl;

import library.config.security.AuthenticationFacade;
import library.dto.UserBookDto;
import library.exception.CustomException;
import library.models.Book;
import library.models.User;
import library.models.UserBook;
import library.repository.BookRepo;
import library.repository.UserRepo;
import library.services.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService {

    private final AuthenticationFacade facade;
    private final UserRepo userRepo;
    private final BookRepo bookRepo;

    @Value("${default-borrow-limit}")
    private Integer borrowLimit;
    @Value("${default-borrow-period-limit}")
    private Long borrowPeriodLimit;


    @Override
    public String borrowBook(Long bookId) {
        long userId = facade.getAuthentication().getUserId();
        User user = userRepo.findById(userId).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if (user.getBorrowedBookCount() >= borrowLimit)
            throw new CustomException("Maximum borrow limit is " + borrowLimit, HttpStatus.BAD_REQUEST);
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new CustomException("Book not found", HttpStatus.NOT_FOUND));
        if (book.getBookCount() >= 1) {
            UserBook userBook = UserBook.builder()
                    .borrowedDate(LocalDate.now())
                    .expectedReturnDate(LocalDate.now().plusMonths(borrowPeriodLimit))
                    .userId(userId)
                    .bookId(book.getId())
                    .build();

        }
        return "";
    }

    @Override
    public String returnBook(Long bookId) {
        return "";
    }

    @Override
    public UserBookDto getById(Long userBookId) {
        return null;
    }

    @Override
    public List<UserBookDto> getByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<UserBookDto> getAll() {
        return List.of();
    }
}
