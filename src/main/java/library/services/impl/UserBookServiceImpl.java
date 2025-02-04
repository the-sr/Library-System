package library.services.impl;

import library.config.security.AuthenticationFacade;
import library.dto.UserBookDto;
import library.enums.RequestType;
import library.exception.CustomException;
import library.models.Book;
import library.models.User;
import library.models.UserBook;
import library.repository.BookRepo;
import library.repository.UserBookRepo;
import library.repository.UserRepo;
import library.services.UserBookService;
import library.services.mappers.UserBookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService {

    private final AuthenticationFacade facade;
    private final UserRepo userRepo;
    private final BookRepo bookRepo;
    private final UserBookRepo userBookRepo;
    private final UserBookMapper userBookMapper;

    @Value("${default-borrow-limit}")
    private Integer borrowLimit;
    @Value("${default-borrow-period-limit}")
    private Long borrowPeriodLimit;


    @Transactional
    @Override
    public String borrowBook(Long bookId) {
        long userId = facade.getAuthentication().getUserId();
        User user = userRepo.findById(userId).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        if (user.getBorrowedBookCount() >= borrowLimit)
            throw new CustomException("Maximum borrow limit is " + borrowLimit, HttpStatus.BAD_REQUEST);
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new CustomException("Book not found", HttpStatus.NOT_FOUND));
        if(userBookRepo.findByUserIdAndBookId(userId,bookId).isPresent())
            throw new CustomException("Book already borrowed", HttpStatus.CONFLICT);
        if (book.getBookCount() < 1)
            throw new CustomException("Book is out of stock", HttpStatus.BAD_REQUEST);
        UserBook userBook = UserBook.builder()
                .borrowedDate(LocalDate.now())
                .expectedReturnDate(LocalDate.now().plusMonths(borrowPeriodLimit))
                .requestType(RequestType.BORROW)
                .userId(userId)
                .bookId(book.getId())
                .build();
        userBookRepo.save(userBook);
        user.setBorrowedBookCount(user.getBorrowedBookCount() + 1);
        userBookRepo.save(userBook);
        book.setBookCount(book.getBookCount() - 1);
        bookRepo.save(book);
        return "Book borrow requested successfully, please visit library to pick your book";
    }

    @Override
    public String returnBook(Long bookId) {
        long userId = facade.getAuthentication().getUserId();
        UserBook userBook=userBookRepo.findByUserIdAndBookId(userId,bookId).orElseThrow(() -> new CustomException("You haven't borrowed this book yet", HttpStatus.BAD_REQUEST));
        double fineAmount=0;
        if(LocalDate.now().isAfter(userBook.getExpectedReturnDate()))
            fineAmount=2500;
        userBook.setFineAmount(fineAmount);
        userBook.setRequestType(RequestType.RETURN);
        userBookRepo.save(userBook);
        if(fineAmount>0){
            return "Book return requested successfully, please visit library with fine amount to return the book";
        }else return "Book return requested successfully, please visit library to return book";
    }

    @Override
    public UserBookDto getById(Long userBookId) {
        return userBookMapper.entityToDto(userBookRepo.findById(userBookId).orElseThrow(() -> new CustomException("User Book not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<UserBookDto> getAll(Long userId, String requestType, Boolean isActive) {
        List<UserBook> userBookList=userBookRepo.findAll(userId,requestType,isActive);
        return userBookList.parallelStream().map(userBookMapper::entityToDto).collect(Collectors.toList());
    }
}
