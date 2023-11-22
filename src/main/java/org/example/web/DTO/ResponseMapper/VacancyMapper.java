package org.example.web.DTO.ResponseMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Vacancy;
import org.example.web.DTO.vacancy.VacancyCreateDTO;
import org.example.web.DTO.vacancy.VacancyResponseDTO;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VacancyMapper {

    public static Vacancy dtoToVacancy(VacancyCreateDTO dto) {
        return new ModelMapper().map(dto, Vacancy.class);
    }

    public static VacancyResponseDTO vacancyToDto(Vacancy vacancy) {
        return new ModelMapper().map(vacancy, VacancyResponseDTO.class);
    }
}
