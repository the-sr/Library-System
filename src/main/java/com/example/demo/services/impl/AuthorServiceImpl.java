package com.example.demo.services.impl;

import com.example.demo.dto.AuthorDto;
import com.example.demo.models.Author;
import com.example.demo.repository.AuthorRepo;
import com.example.demo.services.AuthorService;
import com.example.demo.services.mappers.AuthorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;
    private final AuthorMapper authorMapper;

    @Override
    public Author addAuthor(AuthorDto authorDto) {
        Author author = authorMapper.dtoToEntity(authorDto);
        author.setId(authorRepo.findNextId());
        return authorRepo.save(author);
    }
}
