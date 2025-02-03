package library.services.impl;

import library.models.Author;
import library.repository.AuthorRepo;
import library.services.mappers.AuthorMapper;
import org.springframework.stereotype.Service;

import library.dto.AuthorDto;
import library.services.AuthorService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepo;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto add(AuthorDto req) {
        Author author=authorRepo.findByEmail(req.getEmail()).orElse(null);
        return authorMapper.entityToDto(Objects.requireNonNullElseGet(author, () -> authorRepo.save(authorMapper.dtoToEntity(req))));
    }

    @Override
    public List<AuthorDto> getAuthorByBookId(Long bookId) {
        Set<Author> authorSet=authorRepo.findByBookId(bookId);
        return authorSet.parallelStream().map(authorMapper::entityToDto).collect(Collectors.toList());
    }

}
