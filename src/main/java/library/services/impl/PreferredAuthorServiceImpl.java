package library.services.impl;

import library.config.security.AuthenticationFacade;
import library.dto.AuthorDto;
import library.models.PreferredAuthor;
import library.repository.PreferredAuthorRepo;
import library.services.PreferredAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferredAuthorServiceImpl implements PreferredAuthorService {

    private final PreferredAuthorRepo preferredAuthorRepo;
    private final AuthenticationFacade facade;

    @Override
    public String addPreferredAuthor(List<AuthorDto> req) {
        long userId=facade.getAuthentication().getUserId();
        if(req!=null && !req.isEmpty()){
            req.parallelStream().forEach(authorDto -> {
                PreferredAuthor preferredAuthor=PreferredAuthor.builder()
                        .userId(userId)
                        .authorId(authorDto.getId())
                        .build();
                preferredAuthorRepo.save(preferredAuthor);
            });
        }
        return "Preferred author added successfully";
    }
}

