package org.example.web.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPasswordDTO {

    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
