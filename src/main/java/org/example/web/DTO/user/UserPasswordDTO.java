package org.example.web.DTO.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDTO {


    @NotBlank()
    @Size(min = 4, max = 10)
    private String currentPassword;

    @NotBlank()
    @Size(min = 4, max = 10)
    private String newPassword;

    @NotBlank()
    @Size(min = 4, max = 10)
    private String confirmNewPassword;
}
