package org.example.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.JWT.JwtUserDetails;
import org.example.entity.Client;
import org.example.entity.User;
import org.example.exception.CpfUniqueViolationException;
import org.example.service.ClientService;
import org.example.service.UserService;
import org.example.web.DTO.ResponseMapper.ClientMapper;
import org.example.web.DTO.client.ClientCreateDTO;
import org.example.web.DTO.client.ClientResponseDTO;
import org.example.web.DTO.user.UserResponseDTO;
import org.example.web.exception.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "Contem todas as operaçoes relativas aos recursos sobre edição e leitura de um cliente.")
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;



    @Operation(summary = "Criar um novo cliente", description = "Recurso para criar um novo cliente",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "cliente criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "cliente CPF ja cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "recursos nao processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "cliente não encontrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "cliente sem permissão para acessarus" +
                            " este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid ClientCreateDTO dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails) throws CpfUniqueViolationException {
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.getById(userDetails.getId()));
        clientService.save(client);
        return ResponseEntity.status(201).body(ClientMapper.toDTO(client));
    }
}
