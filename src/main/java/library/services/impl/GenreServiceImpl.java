package library.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import library.dto.GenreDto;
import library.models.Genre;
import library.repository.GenreRepo;
import library.services.GenreService;

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
