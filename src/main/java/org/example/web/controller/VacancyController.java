package org.example.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.Vacancy;
import org.example.service.VacancyService;
import org.example.web.DTO.ResponseMapper.VacancyMapper;
import org.example.web.DTO.user.UserResponseDTO;
import org.example.web.DTO.vacancy.VacancyCreateDTO;
import org.example.web.DTO.vacancy.VacancyResponseDTO;
import org.example.web.exception.ErrorMessage;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
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

    @Operation(summary = "Criar uma nova vaga", description = "Recurso para criar uma nova vaga",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "vaga criada com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL do recurso criado")),
                    @ApiResponse(responseCode = "409", description = "vaga ja cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "recursos nao processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "vaga não encontrada no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
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


    @Operation(summary = "Buscar vaga por código", description = "Buscar vaga por código",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "vaga encontrada com sucesso",
                            content = @Content(mediaType = "application/json;charset+UTF-8",
                            schema = @Schema(implementation = VacancyResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "vaga não encontrada no sistema",
                            content = @Content(mediaType = "application/json;charset+UTF-8", schema = @Schema(implementation = VacancyResponseDTO.class))),
            }
    )
    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VacancyResponseDTO> getByCode(@PathVariable String code) {
        Vacancy vacancy = vacancyService.getByCode(code);
        return ResponseEntity.ok(VacancyMapper.vacancyToDto(vacancy));
    }

}
