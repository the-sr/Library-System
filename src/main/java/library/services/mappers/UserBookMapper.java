package library.services.mappers;

import library.dto.UserBookDto;
import library.models.UserBook;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserBookMapper extends MapperInterface<UserBook, UserBookDto> {

    UserBook dtoToEntity(UserBookDto userBookDto);

    UserBookDto entityToDto(UserBook userBook);
}
