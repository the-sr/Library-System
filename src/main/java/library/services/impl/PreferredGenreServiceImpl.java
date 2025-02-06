package library.services.impl;

import library.config.security.AuthenticationFacade;
import library.dto.GenreDto;
import library.models.PreferredGenre;
import library.repository.PreferredGenreRepo;
import library.services.PreferredGenreService;
import library.services.mappers.GenreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferredGenreServiceImpl implements PreferredGenreService {

    private final PreferredGenreRepo preferredGenreRepo;
    private final AuthenticationFacade facade;
    private final GenreMapper genreMapper;

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
            return "Genre has been successfully added to your preferred genre";
        }else return "Please select at least one genre";
    }

    @Override
    public List<GenreDto> getPreferredGenres(long userId) {
        List<PreferredGenre> preferredGenreList = preferredGenreRepo.findAllByUserId(userId);
        List<GenreDto> res = new ArrayList<>();
        if (preferredGenreList != null && !preferredGenreList.isEmpty())
            preferredGenreList.parallelStream().forEach(preferredGenre -> res.add(genreMapper.entityToDto(preferredGenre.getGenre())));
        return res;
    }

    @Override
    public String removePreferredGenre(long genreId) {
        preferredGenreRepo.deleteAllByUserIdAndGenreId(facade.getAuthentication().getUserId(), genreId);
        return "Genre has been successfully removed from your preferred genre";
    }
}

