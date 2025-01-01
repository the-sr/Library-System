package com.example.demo.services;

import com.example.demo.dto.UserBookDto;

import java.util.List;

public interface UserBookService {
    String borrowBook(long bookId);

    String returnBook(long bookId);

    List<UserBookDto> getUserBookByUserId(long userId);

    UserBookDto getUserBookByBookId(long bookId);

    UserBookDto getAllUserBooks();
}
