package library.services.impl;

import library.config.security.AuthenticationFacade;
import library.dto.GenreDto;
import library.models.PreferredGenre;
import library.repository.PreferredGenreRepo;
import library.services.PreferredGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferredGenreServiceImpl implements PreferredGenreService {

    private final PreferredGenreRepo preferredGenreRepo;
    private final AuthenticationFacade facade;

    @Override
    public String addPreferredGenre(List<GenreDto> req) {
        long userId=facade.getAuthentication().getUserId();
        if(req!=null && !req.isEmpty()){
            req.parallelStream().forEach(genreDto -> {
                PreferredGenre preferredGenre=PreferredGenre.builder()
                        .userId(userId)
                        .genreId(genreDto.getId())
                        .build();
                preferredGenreRepo.save(preferredGenre);
            });
        }
        return "Preferred genres added successfully";
    }
}

