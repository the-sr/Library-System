package library.controller;

import io.swagger.v3.oas.annotations.Operation;
import library.services.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserBookController {

    private final UserBookService userBookService;

    @Operation(summary = "Sent book borrow request")
    @PostMapping("/borrow-request")
    public ResponseEntity<?> borrowRequest(@RequestParam Long bookId){
        return ResponseEntity.ok().body(userBookService.borrowRequest(bookId));
    }

    @Operation(summary = "Sent book return request")
    @PostMapping("/return-request")
    public ResponseEntity<?> returnRequest(@RequestParam Long bookId){
        return ResponseEntity.ok().body(userBookService.returnRequest(bookId));
    }

    @Operation(summary = "Get user book details")
    @GetMapping("/user-book/{userBookId}")
    public ResponseEntity<?> getUserBookById(@PathVariable Long userBookId){
        return ResponseEntity.ok(userBookService.getById(userBookId));
    }

    @Operation(summary = "Get user books by user id or request type or active status")
    @GetMapping("/user-books")
    public ResponseEntity<?> getUserBooks(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String requestType,
            @RequestParam(required = false) Boolean isActive){
        return ResponseEntity.ok(userBookService.getAll(userId,requestType,isActive));
    }

    @PutMapping("/user-book/handle-borrow-request")
    public ResponseEntity<?> handleBorrowRequest(@RequestParam Long userBookId){
        return ResponseEntity.ok().body(userBookService.handelBorrowRequest(userBookId));
    }

    @PutMapping("/user-book/handle-return-request")
    public ResponseEntity<?> handleReturnRequest(@RequestParam Long userBookId){
        return ResponseEntity.ok().body(userBookService.handelReturnRequest(userBookId));
    }
}
