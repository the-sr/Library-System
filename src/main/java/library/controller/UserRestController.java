package library.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import library.dto.UserDto;
import library.dto.req.ResetPassReq;
import library.services.UserService;
import library.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserRestController {

    private final UserService userService;

    @Operation(summary = "User Registration")
    @PostMapping("/register")
    public ResponseEntity<?> save(@Valid @RequestBody UserDto user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @Operation(summary = "Get user by ID")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @Operation(summary = "Update user by ID")
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @Valid @RequestBody UserDto user) {
        return ResponseEntity.ok().body(userService.updateById(id, user));
    }

    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userService.deleteById(id));
    }

    @Operation(summary = "Get all users")
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
        return ResponseEntity.ok().body(userService.changePassword(passReq));
    }
}
