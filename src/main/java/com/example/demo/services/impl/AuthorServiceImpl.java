package com.example.demo.services.impl;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.payloads.req.AuthorReq;
import com.example.demo.payloads.res.AuthorRes;
import com.example.demo.repository.BookRepo;
import com.example.demo.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final BookRepo bookRepo;

    @Override
    public AuthorRes addAuthor(AuthorReq authorReq, long bookId) {
        Author author = Author.builder()
                .firstName(authorReq.getFirstName())
                .lastName(authorReq.getLastName())
                .email(authorReq.getEmail())
                .phone(authorReq.getPhone())
                .books((Set<Book>) bookRepo.findById(bookId).get())
                .build();
        return null;
    }
}
