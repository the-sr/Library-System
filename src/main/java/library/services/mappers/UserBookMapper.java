package library.services.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import library.dto.UserBookDto;
import library.models.UserBook;

@Mapper(componentModel = "spring")
public interface UserBookMapper extends MapperClass<UserBook, UserBookDto, Void> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    UserBook dtoToEntity(UserBookDto dto);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    UserBookDto entityToDto(UserBook userBook);

}
