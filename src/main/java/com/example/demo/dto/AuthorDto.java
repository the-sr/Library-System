package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthorDto {

    private long id;
    @NotNull(message = "First Name is required.")
    private String firstname;
    @NotNull(message = "First Name is required.")
    private String lastname;
    @Email(message = "Invalid Email Format, Please provide a valid email")
    private String email;
    private String phone;
}
