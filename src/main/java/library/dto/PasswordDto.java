package library.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
    private String oldPassword;
    private String newPassword;
    private String email;
    private Integer otp;
    private String token;
}
