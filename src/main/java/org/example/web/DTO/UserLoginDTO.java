package org.example.web.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDTO {

    private String user;

    @NotBlank()
    @Size(min = 4, max = 10)
    private String password;
}
