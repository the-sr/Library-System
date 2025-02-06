package library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import library.enums.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "First name cannot be blank")
    private String firstName;

    private String middleName;

    @NotEmpty(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Invalid Email")
    private String email;

    @Length(min = 5, max = 15, message = "Password must be 5 to 15 character long")
    private String password;

    private String confirmPassword;

    private Role role;

    private String phone;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    private Integer borrowedBookCount;

    private Boolean isActive;

    private List<AddressDto> address;
}