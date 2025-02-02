package library.services;

import library.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    AuthorDto add(AuthorDto req);
    List<AuthorDto> getAuthorByBookId(Long bookId);
}
