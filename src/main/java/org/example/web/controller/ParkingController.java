package org.example.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.ClientVacancy;
import org.example.service.ParkService;
import org.example.web.DTO.ResponseMapper.ClientVacancyMapper;
import org.example.web.DTO.park.ParkCreateDTO;
import org.example.web.DTO.park.ParkResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/park")
@RequiredArgsConstructor
public class ParkingController {

    public final ParkService parkService;

    @PostMapping("/checkin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkResponseDTO> checkin(@RequestBody @Valid ParkCreateDTO parkCreateDTO) {
        ClientVacancy clientVacancy = ClientVacancyMapper.toClientVacancy(parkCreateDTO);
        parkService.checkIn(clientVacancy);
        ParkResponseDTO parkResponseDTO = ClientVacancyMapper.toDTO(clientVacancy);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{receipte}")
                .buildAndExpand(clientVacancy.getReceipt())
                .toUri();
        return ResponseEntity.created(location).body(parkResponseDTO);
    }
}
