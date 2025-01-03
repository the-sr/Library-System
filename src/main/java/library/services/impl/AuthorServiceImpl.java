package library.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import library.dto.AuthorDto;
import library.models.Author;
import library.repository.AuthorRepo;
import library.services.AuthorService;
import library.services.mappers.AuthorMapper;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;
    private final AuthorMapper authorMapper;

    @Override
    public Author addAuthor(AuthorDto authorDto) {
        Author author = authorMapper.dtoToEntity(authorDto);
        author.setId(authorRepo.findNextId());
        return authorRepo.save(author);
    }
}
