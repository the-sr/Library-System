package com.example.demo.dto.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRes {
    private String accessToken;

    public LoginRes(String jwt) {
        accessToken = jwt;
    }
}
