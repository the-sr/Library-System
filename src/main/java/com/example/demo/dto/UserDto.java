package com.example.demo.dto;

import com.example.demo.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private long id;

    @NotEmpty(message = "First name cannot be blank")
    private String firstName;

    private String middleName;

    @NotEmpty(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid Email")
    private String email;

    @Length(min = 5, max = 15, message = "Password must be 5 to 15 character long")
    private String password;

    @NotEmpty(message = "Confirm Password is required")
    private String confirmPassword;

    private Role role;

    private String phone;

    private List<AddressDto> address;

    private int borrowedBookCount;
}
