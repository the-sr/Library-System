package com.example.demo.services.impl;

import com.example.demo.models.Author;
import com.example.demo.payloads.req.AuthorReq;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;

    @Override
    public Author addAuthor(AuthorReq authorReq) {
        Author author = Author.builder()
                .id(authorRepo.findNextId())
                .firstName(authorReq.getFirstname())
                .lastName(authorReq.getLastname())
                .email(authorReq.getEmail())
                .phone(authorReq.getPhone())
                .build();
        return authorRepo.save(author);
    }
}
