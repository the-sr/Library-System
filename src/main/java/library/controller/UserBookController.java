package library.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import library.services.UserBookService;

@RestController
@AllArgsConstructor
@RequestMapping("/book")
public class UserBookController {

    private final UserBookService userBookService;

    @GetMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam Long bookId) {
        return ResponseEntity.ok().body(userBookService.borrowBook(bookId));
    }

    @GetMapping("/return")
    public ResponseEntity<?> returnBook(@RequestParam Long bookId) {
        return ResponseEntity.ok().body(userBookService.returnBook(bookId));
    }
}
