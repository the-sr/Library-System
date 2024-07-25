package com.example.demo.payloads.req;

import com.example.demo.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserReq {
    @NotBlank(message = "First name cannot be blank.")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name cannot be blank.")
    private String lastName;

    @Email(message = "Invalid Email.")
    private String email;

    @Length(min = 5,max = 15,message = "Password must be 5 to 15 character long")
    private String password;

    private String confirmPassword;

    private Role role;

    private String phone;

    private List<AddressReq> address;
}
