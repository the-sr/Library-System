package com.example.demo.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class BookRes {
    private String isbn;
    private String title;
    private String edition;
    private String publisher;
    private int bookCount;
    private Set<AuthorRes> authors;
    private List<GenreRes> genre;
}
