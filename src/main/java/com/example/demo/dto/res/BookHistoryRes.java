package com.example.demo.dto.res;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.UserBookDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookHistoryRes {
    private BookDto book;
    private UserBookDto userBookDto;
}
