package com.example.demo.services.impl;

import com.example.demo.dto.AuthorDto;
import com.example.demo.models.Author;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;

    @Override
    public Author addAuthor(AuthorDto authorDto) {
        Author author = Author.builder()
                .id(authorRepo.findNextId())
                .firstName(authorDto.getFirstname())
                .lastName(authorDto.getLastname())
                .email(authorDto.getEmail())
                .phone(authorDto.getPhone())
                .build();
        return authorRepo.save(author);
    }
}
