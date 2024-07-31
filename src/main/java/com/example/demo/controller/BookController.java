package com.example.demo.controller;

import com.example.demo.payloads.req.BookReq;
import com.example.demo.services.BookService;
import com.example.demo.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class BookController {

    private BookService bookService;


    @PostMapping("/add-book")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookReq book){
        return ResponseEntity.ok().body(bookService.saveBook(book));
    }

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks(){
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping("/page-wise-books/{pageNumber}")
    public ResponseEntity<?> getAllBooksPageWise(
            @PathVariable("pageNumber") int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDirection",defaultValue = AppConstants.SORT_DIRECTION,required = false) String sortDirection){
        return ResponseEntity.ok().body(bookService.getAllBooksPagewise(pageNumber,pageSize,sortBy,sortDirection));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") long id){
        return ResponseEntity.ok().body(bookService.getBookById(id));
    }

    @GetMapping("/book")
    public ResponseEntity<?> getBookByTitle(@RequestParam("title") String title){
        return ResponseEntity.ok().body(bookService.getBookByTitle(title));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateBook(@RequestBody BookReq book){
        return null;
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") String id){
        return null;
    }

}
