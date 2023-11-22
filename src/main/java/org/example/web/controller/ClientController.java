package org.example.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.JWT.JwtUserDetails;
import org.example.entity.Client;
import org.example.exception.CpfUniqueViolationException;
import org.example.repository.projection.ClientProjection;
import org.example.service.ClientService;
import org.example.service.UserService;
import org.example.web.DTO.PageableDTO;
import org.example.web.DTO.ResponseMapper.ClientMapper;
import org.example.web.DTO.ResponseMapper.PageableMapper;
import org.example.web.DTO.client.ClientCreateDTO;
import org.example.web.DTO.client.ClientResponseDTO;
import org.example.web.DTO.user.UserResponseDTO;
import org.example.web.exception.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Buscar cliente por Id", description = "Buscar cliente por Id",
        security = @SecurityRequirement(name = "security"),
        responses = {
                @ApiResponse(responseCode = "200", description = "cliente encontrado com sucesso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "404", description = "cliente não encontrado no sistema",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "403", description = "cliente sem permissão para acessae este recurso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDTO> getById(@PathVariable Long id) {
        Client client = clientService.getById(id);
        ClientResponseDTO dto = ClientMapper.toDTO(client);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Buscar todos clientes", description = "Buscar todos os clientes",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                @Parameter(in = ParameterIn.QUERY, name = "page",
                        content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                        description = "Representa pagina retornada"
                ),
                @Parameter(in = ParameterIn.QUERY, name = "sort",
                        content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                        description = "Representa total de elementos por pagina"
                ),
                @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true,
                        content = @Content(schema = @Schema(type = "string", defaultValue = "id,asc")),
                        description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação"
                ),
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "cliente encontrado com sucesso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "404", description = "cliente não encontrado no sistema",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "403", description = "cliente sem permissão para acessae este recurso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> getAll(@Parameter(hidden = true) @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        Page<ClientProjection> clients = clientService.getAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDTO(clients));
    }


    @Operation(summary = "Buscar todos clientes", description = "Buscar todos os clientes",
            security = @SecurityRequirement(name = "security"),
            responses = {
            @ApiResponse(responseCode = "200", description = "cliente encontrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "cliente não encontrado no sistema",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "cliente sem permissão para acessae este recurso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        }
    )
    @GetMapping("/details")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDTO> getDetails(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Client client = clientService.findByUserId(userDetails.getId());
        return ResponseEntity.ok(ClientMapper.toDTO(client));
    }
}
