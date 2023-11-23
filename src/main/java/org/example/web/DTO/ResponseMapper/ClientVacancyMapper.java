package org.example.web.DTO.ResponseMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.ClientVacancy;
import org.example.web.DTO.park.ParkCreateDTO;
import org.example.web.DTO.park.ParkResponseDTO;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientVacancyMapper {

    public static ParkResponseDTO toDTO(ClientVacancy clientVacancy) {
        return new ModelMapper().map(clientVacancy, ParkResponseDTO.class);
    }


    public static ClientVacancy toClientVacancy(ParkCreateDTO parkCreateDTO) {
        return new ModelMapper().map(parkCreateDTO, ClientVacancy.class);
    }
}
