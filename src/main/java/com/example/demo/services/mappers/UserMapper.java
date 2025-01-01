package com.example.demo.services.mappers;

import com.example.demo.models.User;
import com.example.demo.dto.UserDto;
import com.example.demo.projection.UserProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper extends MapperClass<User, UserDto, UserProjection>{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "borrowedBookCount", ignore = true)
    @Mapping(target = "address", ignore = true)
    User dtoToEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "confirmPassword", ignore = true)
    @Mapping(target = "address", ignore = true)
    UserDto entityToDto(User user);

    UserDto projectionToDto(UserProjection userProjection);

    @Named("encodePassword")
     default String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
