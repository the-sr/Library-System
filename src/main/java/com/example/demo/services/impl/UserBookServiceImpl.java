package com.example.demo.services.impl;

import com.example.demo.config.Security.AuthenticationFacade;
import com.example.demo.exception.CustomException;
import com.example.demo.models.Book;
import com.example.demo.models.User;
import com.example.demo.models.UserBook;
import com.example.demo.repository.BookRepo;
import com.example.demo.repository.UserBookRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.services.UserBookService;
import com.example.demo.utils.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService {

    private static final int FINE_AMOUNT = 50;
    private static final int MAX_BORROWING = 5;
    private final UserRepo userRepo;
    private final BookRepo bookRepo;
    private final UserBookRepo userBookRepo;
    private final EmailService emailService;
    private final AuthenticationFacade facade;

    @Override
    public String borrowBook(long bookId) {
        long userId = facade.getAuthentication().getUserId();
        User user = userRepo.findById(userId).get();
        if (user.getBorrowedBookCount() >= MAX_BORROWING)
            throw new CustomException("Your maximum borrowing limit is full. Please return a book to borrow new one.", HttpStatus.SERVICE_UNAVAILABLE);
        Book book = bookRepo.findById(bookId).get();
        if (book.getBookCount() > 0) {
            UserBook userBook = UserBook.builder()
                    .id(userBookRepo.findNextId())
                    .user(user)
                    .book(book)
                    .borrowDate(LocalDate.now())
                    .returnDate(LocalDate.now().plusMonths(6))
                    .build();
            userBookRepo.save(userBook);
            book.setBookCount(book.getBookCount() - 1);
            bookRepo.save(book);
        }
        emailService.sendMail(user.getEmail(), "Book Borrowing", "Book Borrowed Successfully");
        return "Book Borrowed Successfully";
    }

    @Override
    public String returnBook(long bookId) {
        long userId = 1;
        UserBook userBook = userBookRepo.findByUserIdAndBookId(userId, bookId);
        if (userBook.getOverDueDays() > 0) {
            float fineAmount = userBook.getOverDueDays() * FINE_AMOUNT;
            return "You kept this book for more than expected return date. Your overdue days is " +
                    userBook.getOverDueDays() + " .Your total fine amount is Rs." + fineAmount +
                    " @Rs." + FINE_AMOUNT + " per day. Please pay fine first.";
        }
        userBookRepo.deleteById(userBook.getId());
        return "Book Returned Successfully";
    }

    @Scheduled(cron = "0 0 * * * *")
    private void calculateOverDueDays() {
        List<UserBook> userBookList = userBookRepo.findAll();
        userBookList.forEach(userBook -> {
            if (LocalDate.now().isAfter(userBook.getReturnDate())) {
                long overDueDays = ChronoUnit.DAYS.between(userBook.getReturnDate(), LocalDate.now());
                userBook.setOverDueDays((int) overDueDays);
                userBookRepo.save(userBook);
            } else {
                userBook.setOverDueDays(0);
                userBookRepo.save(userBook);
            }
        });
    }
}
