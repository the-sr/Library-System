package library.services.impl;

import library.models.Genre;
import library.repository.GenreRepo;
import library.services.mappers.GenreMapper;
import org.springframework.stereotype.Service;

import library.dto.GenreDto;
import library.services.GenreService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepo genreRepo;
    private  final GenreMapper genreMapper;

    @Override
    public GenreDto add(GenreDto req) {
        Genre genre=genreRepo.findByName(req.getName()).orElse(null);
        if(genre!=null)
            return genreMapper.entityToDto(genre);
        else return genreMapper.entityToDto(genreRepo.save(genreMapper.dtoToEntity(req)));
    }

}
