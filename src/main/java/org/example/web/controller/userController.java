package org.example.web.controller;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User userById = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userById);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestBody User user) {
        User updated = userService.update(id, user);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable long id,
                       @RequestBody User user) {

        User userUpdated = userService.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }
}
