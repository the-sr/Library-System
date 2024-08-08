package com.example.demo.services.mappers;

import com.example.demo.models.User;
import com.example.demo.payloads.req.UserReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/* Mapper is an annotation of MapStruct which help to implement the mapper interface at compile time,
    thus making system faster.
---> componentModel : is used to define the bean type (spring, cdi)
---> uses : is used to define which mapper is used by this mapper interface. Like this mapper has used
    user details mapper
*/

@Mapper(componentModel = "spring",uses = {UserDetailsMapper.class})
public interface UserDetailsMapper extends MapperClass<User, UserReq> {

    /*
    ---> Mappings is used to wrap all the mapper at once.
    ---> Mapping is used to define source and target field if field name is different.
         expression :  is used to map calculated value like target="age" can store value from
            expression="java(calculateAge(ENTITY_NAME.getFieldName()))
    ---> ignore : is used to ignore field.
     */

    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "password", expression = "java(encodePassword(userReq.getPassword()))"),
            @Mapping(target = "address", ignore = true)
    })
    User toEntity(UserReq userReq);


    @Mappings({
            @Mapping(target = "address", ignore = true)
    })
    UserReq toDto(User user);

    default String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
