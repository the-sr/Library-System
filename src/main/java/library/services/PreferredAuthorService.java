package library.services;

import library.dto.AuthorDto;

import java.util.List;

public interface PreferredAuthorService {
    String addPreferredAuthor(List<AuthorDto> req);
    List<AuthorDto> getPreferredAuthors(long userId);
    String removePreferredAuthor(long authorId);
}
