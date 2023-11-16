package org.example.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Contem todas as operaçoes relativas aos recursos sobre edição e leitura de uma usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class userController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User userById = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Mapper.userToDto(userById));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<User> all = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toListDto(all));
    }

    @Operation(summary = "Criar um novo usuario", description = "Recurso para criar um novo usuario",
            responses = {

                @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                @ApiResponse(responseCode = "409", description = "usuario ja cadastrado no sitema",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "recursos nao processado por dados de entrada invalidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO userDTO) {
        User savedUser = userService.save(Mapper.dtoToUser(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.userToDto(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestBody User user) {
        User updated = userService.update(id, user);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
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
