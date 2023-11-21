package org.example.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exception.PasswordInvalidException;
import org.example.service.UserService;
import org.example.web.DTO.UserCreateDTO;
import org.example.web.DTO.UserPasswordDTO;
import org.example.web.DTO.UserResponseDTO;
import org.example.web.DTO.ResponseMapper.Mapper;
import org.example.web.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Contem todas as operaçoes relativas aos recursos sobre edição e leitura de uma usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class userController {

    private final UserService userService;

    @Operation(summary = "Buscar um usuario pelo Id", description = "Recurso para encontrar um usuario pelo id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Usuario não encontrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuario sem permissão para acessae este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User userById = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Mapper.userToDto(userById));
    }

    @Operation(summary = "Criar um novo usuario", description = "Recurso para criar um novo usuario",
            security = @SecurityRequirement(name = "security"),
            responses = {
                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "409", description = "usuario ja cadastrado no sitema",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "recursos nao processado por dados de entrada invalidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "404", description = "Usuario não encontrado no sistema",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuario sem permissão para acessae este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }

    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO userDTO) {
        User savedUser = userService.save(Mapper.dtoToUser(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.userToDto(savedUser));
    }

    @Operation(summary = "Buscar todos os usuarios", description = "Buscar todos os usuarios",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios encontrados com sucesso",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
                    @ApiResponse(responseCode = "403", description = "Usuario sem permissão para acessae este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<User> all = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toListDto(all));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestBody User user) {
        User updated = userService.update(id, user);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Alterar senha de um usuario", description = "Alterar senha de um usuario",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "A senha foi alterada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Usuario não encontrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Senhas não conferem",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuario sem permissão para acessae este recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@Valid @PathVariable long id,
                                               @RequestBody UserPasswordDTO dto) throws PasswordInvalidException {

        User userUpdated = userService.updatePassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmNewPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }
}
