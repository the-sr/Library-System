package com.example.demo.services;

import com.example.demo.models.Author;
import com.example.demo.dto.AuthorDto;

public interface AuthorService {

    Author addAuthor(AuthorDto authorDto);

}
