package com.example.demo.controller;

import com.example.demo.payloads.req.ResetPassReq;
import com.example.demo.payloads.req.UserReq;
import com.example.demo.services.UserService;
import com.example.demo.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> save(@Valid @RequestBody UserReq user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @Valid @RequestBody UserReq user) {
        return ResponseEntity.ok().body(userService.updateById(id, user));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userService.deleteById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/page-wise-users/{pageNumber}")
    public ResponseEntity<?> getUsers(
            @PathVariable("pageNumber") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection) {
        return ResponseEntity.ok().body(userService.getAllUsersPagewise(pageNumber, pageSize, sortBy, sortDirection));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassReq passReq) {
        return ResponseEntity.ok().body(userService.resetPassword(passReq));
    }
}
