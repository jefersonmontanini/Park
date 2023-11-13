package org.example.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.web.DTO.UserCreateDTO;
import org.example.web.DTO.UserPasswordDTO;
import org.example.web.DTO.UserResponseDTO;
import org.example.web.DTO.ResponseMapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                               @RequestBody UserPasswordDTO dto) {

        User userUpdated = userService.updatePassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmNewPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }
}
