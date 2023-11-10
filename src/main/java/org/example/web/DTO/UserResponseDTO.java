package org.example.web.DTO;

import lombok.*;
import org.example.entity.User;

import javax.management.relation.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDTO {

    private String user;
    private String password;
    private String role;
}
