package library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import library.config.security.AuthenticationFacade;
import library.dto.UserBookDto;
import library.exception.CustomException;
import library.models.Book;
import library.models.User;
import library.models.UserBook;
import library.repository.BookRepo;
import library.repository.UserBookRepo;
import library.repository.UserRepo;
import library.services.UserBookService;
import library.services.mappers.UserBookMapper;
import library.utils.EmailService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService {

    private static final int FINE_AMOUNT_PER_DAY = 10;
    private static final int MAX_BORROWING = 5;
    private final UserRepo userRepo;
    private final BookRepo bookRepo;
    private final UserBookRepo userBookRepo;
    private final EmailService emailService;
    private final AuthenticationFacade facade;
    private final UserBookMapper userBookMapper;

    @Override
    public String borrowBook(long bookId) {
        long userId = facade.getAuthentication().getUserId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new CustomException("User Not Found", HttpStatus.NOT_FOUND));
        if (user.getBorrowedBookCount() >= MAX_BORROWING)
            throw new CustomException("Your maximum borrowing limit is full. Please return a book to borrow new one.",
                    HttpStatus.SERVICE_UNAVAILABLE);
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new CustomException("Book Not Found", HttpStatus.NOT_FOUND));
        if (book.getBookCount() > 0) {
            UserBook userBook = UserBook.builder()
                    .id(userBookRepo.findNextId())
                    .user(user)
                    .book(book)
                    .borrowDate(LocalDate.now())
                    .expectedReturnDate(LocalDate.now().plusWeeks(24))
                    .build();
            userBookRepo.save(userBook);
            book.setBookCount(book.getBookCount() - 1);
            bookRepo.save(book);
            emailService.sendMail(user.getEmail(), "Book Borrowing", "Book Borrowed Successfully");
            return "Book Borrowed Successfully";
        } else
            throw new CustomException("Book not available currently", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public String returnBook(long bookId) {
        long userId = facade.getAuthentication().getUserId();
        UserBook userBook = userBookRepo.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new CustomException("User hasn't borrowed this book", HttpStatus.NOT_FOUND));
        if (isOverdue(userBook.getExpectedReturnDate())) {
            long overdueDays = getOverdueDays(userBook.getExpectedReturnDate());
            double fineAmount = calculateFineAmount(overdueDays);
            return "You kept this book for more than expected return date. Your overdue days are " +
                    overdueDays + " .Your total fine amount is Rs." + fineAmount +
                    " @ Rs." + FINE_AMOUNT_PER_DAY + " per day. Please pay fine first.";
        }
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new CustomException("Book Not Found", HttpStatus.NOT_FOUND));
        book.setBookCount(book.getBookCount() + 1);
        bookRepo.save(book);
        userBook.setActive(false);
        userBookRepo.save(userBook);
        return "Book Returned Successfully";
    }

    @Override
    public List<UserBookDto> getUserBookByUserId(long userId) {
        List<UserBook> userBookList = userBookRepo.findByUserId(userId);
        List<UserBookDto> userBookDtoList = new ArrayList<>();
        // userBookList.forEach(userBook->userBookDtoList.add())

        return null;
    }

    @Override
    public UserBookDto getUserBookByBookId(long bookId) {
        return null;
    }

    @Override
    public UserBookDto getAllUserBooks() {
        return null;
    }

    private boolean isOverdue(LocalDate date) {
        return LocalDate.now().isAfter(date);
    }

    private long getOverdueDays(LocalDate expectedReturnDate) {
        return ChronoUnit.DAYS.between(LocalDate.now(), expectedReturnDate);
    }

    private double calculateFineAmount(long overdueDays) {
        return overdueDays * FINE_AMOUNT_PER_DAY;
    }
}
