package com.example.demo.services;

import com.example.demo.dto.GenreDto;
import com.example.demo.models.Genre;

public interface GenreService {
    Genre addGenre(GenreDto genreDto);
}
