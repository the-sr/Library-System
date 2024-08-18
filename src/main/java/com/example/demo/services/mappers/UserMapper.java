package com.example.demo.services.mappers;

import com.example.demo.models.User;
import com.example.demo.payloads.req.UserReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper extends MapperClass<User,UserReq>{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "borrowedBookCount", ignore = true)
    @Mapping(target = "address", ignore = true)
    User toEntity(UserReq userReq);

    @Mapping(target = "password", defaultValue = "*********")
    UserReq toDto(User user);

    @Named("encodePassword")
     default String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
