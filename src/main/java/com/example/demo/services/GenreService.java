package com.example.demo.services;

import com.example.demo.models.Genre;
import com.example.demo.dto.req.GenreReq;

public interface GenreService {
    Genre addGenre(GenreReq genreReq);
}
