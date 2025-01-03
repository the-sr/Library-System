package library.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import library.dto.AuthorDto;
import library.models.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends MapperClass<Author, AuthorDto, Void> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author dtoToEntity(AuthorDto authorDto);

    AuthorDto entityToDto(Author author);

}
