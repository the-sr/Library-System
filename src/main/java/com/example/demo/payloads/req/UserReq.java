package com.example.demo.payloads.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

    @Min(value = 5,message = "Password length must be greater than five. ")
    @Max(value = 15,message = "Password length must be less than fifteen.")
    private String password;

    private String confirmPassword;

    private String phone;

    private List<AddressReq> address;
}
