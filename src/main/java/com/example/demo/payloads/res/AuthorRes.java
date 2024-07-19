package com.example.demo.payloads.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRes {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
