package com.example.demo.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
    @NotNull
    @Email
    private String username;
    @NotNull
    private String password;
}
