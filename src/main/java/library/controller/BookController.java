package library.controller;

import jakarta.validation.Valid;
import library.dto.BookDto;
import library.services.BookService;
import library.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
public class BookController {

    private BookService bookService;

    @PostMapping("/add-book")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookDto book) {
        return ResponseEntity.ok().body(bookService.saveUpdateBook(book));
    }

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping("/page-wise-books/{pageNumber}")
    public ResponseEntity<?> getAllBooksPageWise(
            @PathVariable("pageNumber") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) {
        return ResponseEntity.ok().body(bookService.getAllBooksPagewise(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(bookService.getBookById(id));
    }

    @GetMapping("/books-by-title")
    public ResponseEntity<?> getBookByTitle(@RequestParam("title") String title) {
        return ResponseEntity.ok().body(bookService.getBookByTitle(title));
    }

    @GetMapping("/books-by-author")
    public ResponseEntity<?> getBookByAuthor(@RequestParam("author") String author) {
        return ResponseEntity.ok().body(bookService.getBookByAuthor(author));
    }

    @GetMapping("/books-by-isbn")
    public ResponseEntity<?> getBookByISBN(@RequestParam("isbn") String isbn) {
        return ResponseEntity.ok().body(bookService.getBookByISBN(isbn));
    }

    @PutMapping("/book")
    public ResponseEntity<?> updateBook(@RequestBody BookDto book) {
        return ResponseEntity.ok().body(bookService.saveUpdateBook(book));
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(bookService.deleteBookById(id));
    }

}
