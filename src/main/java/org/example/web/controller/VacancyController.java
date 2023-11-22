package org.example.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.Vacancy;
import org.example.service.VacancyService;
import org.example.web.DTO.ResponseMapper.VacancyMapper;
import org.example.web.DTO.vacancy.VacancyCreateDTO;
import org.example.web.DTO.vacancy.VacancyResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vacancy")
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid VacancyCreateDTO dto) {
        Vacancy vacancy = vacancyService.save(VacancyMapper.dtoToVacancy(dto));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(vacancy.getCode())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VacancyResponseDTO> getByCode(@PathVariable String code) {
        Vacancy vacancy = vacancyService.getByCode(code);
        return ResponseEntity.ok(VacancyMapper.vacancyToDto(vacancy));
    }

}
