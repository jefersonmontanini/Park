package org.example.web.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateDTO {

    @Email(message = "O email deve ser valido")
    private String user;
    @NotBlank()
    @Size(min = 4, max = 10)
    private String password;
}
