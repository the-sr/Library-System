package com.example.demo.services.impl;

import com.example.demo.dto.GenreDto;
import com.example.demo.models.Genre;
import com.example.demo.repository.GenreRepo;
import com.example.demo.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepo genreRepo;

    @Override
    public Genre addGenre(GenreDto genreDto) {
        Genre genre = Genre.builder()
                .id(genreRepo.findNextId())
                .name(genreDto.getName())
                .build();
        return genreRepo.save(genre);
    }
}
