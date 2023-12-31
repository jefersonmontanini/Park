package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exception.EntityNotFoundException;
import org.example.exception.PasswordInvalidException;
import org.example.exception.UserNameUniqueViolationException;
import org.example.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository users;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return users.save(user);
        }catch (org.springframework.dao.DataIntegrityViolationException exception) {
            throw new UserNameUniqueViolationException(String.format("Username {%s} ja cadastrado", user.getUser()));
        }
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return users.findById(id)
                .orElseThrow( ()-> new EntityNotFoundException(String.format("Usuario {id=%s} nao encontrado", id)) );
    }

    @Transactional
    public User updatePassword(long id, String currentPassword, String newPassword, String confirmNewPassword) throws PasswordInvalidException {
        if (!newPassword.equals(confirmNewPassword)) {
            throw new PasswordInvalidException(String.format("O campo Nova senha e Confirmar senha devcem ser iguais"));
        }
        return users.findById(id)
                .map( user -> {
                    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                        throw new PasswordInvalidException(String.format("A senha atual está incorreta"));
                    }
                        user.setPassword(passwordEncoder.encode(newPassword));
                        return user;
                } )
                .orElseThrow( ()-> new EntityNotFoundException(String.format("Usuario {id=%s} nao encontrado", id)) );
    }

    @Transactional
    public void delete(long id) {
        users.findById(id)
                .map(user -> {
                    users.delete(user);
                    return user;
                })
                .orElseThrow( ()-> new RuntimeException("Usuario nao encontrado") );
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
     return users.findAll();
    }

    @Transactional
    public User update(Long id, User user) {
        return users.findById(id)
                .map(findedUser -> {
                    user.setId(findedUser.getId());
                    return user;
                } )
                .orElseThrow( ()-> new RuntimeException("Usuario nao encontrado") );
    }

    @Transactional(readOnly = true)
    public User findByUser(String user) {
        return users.findByUser(user)
                .orElseThrow( ()-> new EntityNotFoundException(String.format("Usuario {%s} nao encontrado", user)) );
    }

    @Transactional(readOnly = true)
    public User.Role findRoleByUser(String user) {
        return users.findRoleByUser(user);
    }
}
