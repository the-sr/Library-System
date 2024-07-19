package com.example.demo.controller;

import com.example.demo.payloads.req.LoginReq;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq login) {
//        userService.findByEmail(login.getUsername());
//        validate password
//        generate token
//        respond token
        return ResponseEntity.ok().build();
    }
}
