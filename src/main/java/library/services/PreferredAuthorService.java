package library.services;

import library.dto.AuthorDto;

import java.util.List;

public interface PreferredAuthorService {
    String addPreferredAuthor(List<AuthorDto> req);
}
