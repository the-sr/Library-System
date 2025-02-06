package library.services.impl;

import library.config.security.AuthenticationFacade;
import library.dto.AuthorDto;
import library.models.PreferredAuthor;
import library.repository.PreferredAuthorRepo;
import library.services.PreferredAuthorService;
import library.services.mappers.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PreferredAuthorServiceImpl implements PreferredAuthorService {

    private final PreferredAuthorRepo preferredAuthorRepo;
    private final AuthenticationFacade facade;
    private final AuthorMapper authorMapper;

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
            return "Author has been successfully added to your preferred author";
        }else return "Please select at least one author";
    }

    @Override
    public List<AuthorDto> getPreferredAuthors(long userId) {
        List<PreferredAuthor> preferredAuthorList = preferredAuthorRepo.findAllByUserId(userId);
        List<AuthorDto> res = new ArrayList<>();
        if (preferredAuthorList != null && !preferredAuthorList.isEmpty())
            preferredAuthorList.parallelStream().forEach(preferredAuthor -> res.add(authorMapper.entityToDto(preferredAuthor.getAuthor())));
        return res;
    }

    @Override
    public String removePreferredAuthor(long authorId) {
        preferredAuthorRepo.deleteByUserIdAndAuthorId(facade.getAuthentication().getUserId(), authorId);
        return "Author has been successfully removed from your preferred author";
    }

}

