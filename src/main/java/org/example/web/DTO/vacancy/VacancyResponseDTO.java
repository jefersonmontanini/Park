package org.example.web.DTO.vacancy;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class VacancyResponseDTO {

    private Long id;

    private String code;

    private String status;
}
