package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserBookDto {
    private Long id;
    private UserDto user;
    private BookDto book;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private LocalDate expectedReturnDate;
    private Boolean isActive;
}
