package com.example.demo.services.impl;

import com.example.demo.models.Genre;
import com.example.demo.dto.req.GenreReq;
import com.example.demo.repository.GenreRepo;
import com.example.demo.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepo genreRepo;

    @Override
    public Genre addGenre(GenreReq genreReq) {
        Genre genre = Genre.builder()
                .id(genreRepo.findNextId())
                .name(genreReq.getName())
                .build();
        return genreRepo.save(genre);
    }
}
