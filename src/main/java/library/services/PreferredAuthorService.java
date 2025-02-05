package library.services;

import library.dto.AuthorDto;
import library.dto.GenreDto;

import java.util.List;

public interface PreferredAuthorService {
    String addPreferredAuthor(List<AuthorDto> req);
}
