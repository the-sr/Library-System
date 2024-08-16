package com.example.demo.services;

public interface UserBookService {
    String borrowBook(long bookId);

    String returnBook(long bookId);
}
