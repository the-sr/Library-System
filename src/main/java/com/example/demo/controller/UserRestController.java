package com.example.demo.controller;

import com.example.demo.payloads.req.UserReq;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> save(@Valid @RequestBody UserReq user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello World");
    }
}
