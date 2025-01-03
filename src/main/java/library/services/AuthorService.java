package library.services;

import library.dto.AuthorDto;
import library.models.Author;

public interface AuthorService {

    Author addAuthor(AuthorDto authorDto);

}
