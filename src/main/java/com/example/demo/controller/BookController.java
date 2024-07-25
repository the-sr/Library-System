package com.example.demo.controller;

import com.example.demo.payloads.req.BookReq;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class BookController {

    @PostMapping("/add-book")
    public ResponseEntity<?> addBook(BookReq book){
        return null;
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") String id){
        return null;
    }

    @GetMapping("/book/:title")
    public ResponseEntity<?> getBookByTitle(@RequestParam("title") String title){
        return null;
    }

    @PutMapping("/book/:id")
    public ResponseEntity<?> updateBook(@RequestBody BookReq book){
        return null;
    }

    @DeleteMapping("/book/:id")
    public ResponseEntity<?> deleteBook(@PathVariable("id") String id){
        return null;
    }

    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks(){
        return null;
    }
}
