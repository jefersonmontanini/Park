package org.example.web.DTO.client;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.User;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ClientCreateDTO {

    @NotBlank
    @Size(min = 5, max = 100 )
    private String name;

    @Size(min = 11, max = 11)
    @CPF
    private String cpf;

}
