package com.example.demo.payloads.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookRes {
    private String isbn;
    private String title;
    private String edition;
    private String publisher;
    private int bookCount;
    private List<AuthorRes> authors;
}
