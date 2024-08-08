package com.example.demo.services.mappers;

import com.example.demo.models.Genre;
import com.example.demo.payloads.req.GenreReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {GenreDetailsMapper.class})
public interface GenreDetailsMapper extends MapperClass<Genre, GenreReq> {
    Genre toEntity(GenreReq genreReq);

    GenreReq toDto(Genre genre);
}
