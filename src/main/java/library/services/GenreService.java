package library.services;

import library.dto.GenreDto;
import library.models.Genre;

public interface GenreService {
    Genre addGenre(GenreDto genreDto);
}
