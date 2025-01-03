package library.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import library.dto.BookDto;
import library.models.Book;

@Mapper(componentModel = "spring")
public interface BookMapper extends MapperClass<Book, BookDto, Void> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genre", ignore = true)
    Book dtoToEntity(BookDto bookDto);

    BookDto entityToDto(Book book);

}
