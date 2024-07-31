package com.example.demo.services;

import com.example.demo.models.Genre;
import com.example.demo.payloads.req.GenreReq;

public interface GenreService {
    Genre addGenre(GenreReq genreReq);
}
