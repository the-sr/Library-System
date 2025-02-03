package library.controller;

import jakarta.validation.Valid;
import library.dto.BookDto;
import library.services.BookService;
import library.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/add-book")
    public ResponseEntity<?> addBook(@RequestBody BookDto req){
        return ResponseEntity.ok().body(bookService.add(req));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id){
        return ResponseEntity.ok().body(bookService.findById(id));
    }

    @GetMapping("/books-by-title")
    public ResponseEntity<?> getBookByTitle(@RequestParam String title){
        return ResponseEntity.ok().body(bookService.findByTitle(title));
    }

    @GetMapping("/books-by-author")
    public ResponseEntity<?> getBookByAuthor(@RequestParam String author){
        return ResponseEntity.ok().body(bookService.findByAuthor(author));
    }

    @GetMapping("/book-by-genre")
    public ResponseEntity<?> getBookByGenre(@RequestParam String genre){
        return ResponseEntity.ok().body(bookService.findByGenre(genre));
    }

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks(){
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping("/page-wise-books")
    public ResponseEntity<?> getBooksPagewise(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) {
        return ResponseEntity.ok().body(bookService.getAllBooks(pageNumber, pageSize, sortBy, sortDirection));
    }

    @PutMapping("/book")
    public ResponseEntity<?> updateBook(@Valid @RequestBody BookDto req) {
        return ResponseEntity.ok().body(bookService.updateById(req));
    }

    @DeleteMapping("book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(bookService.removeById(id));
    }

}
