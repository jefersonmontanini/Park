package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.exception.EntityNotFoundException;
import org.example.exception.UserNameUniqueViolationException;
import org.example.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository users;

    @Transactional
    public User save(User user) {
        try {
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
    public User updatePassword(long id, String currentPassword, String newPassword, String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) {
            throw new RuntimeException("O campo Nova senha e Confirmar senha devcem ser iguais");
        }
        return users.findById(id)
                .map( user -> {
                    if (!user.getPassword().equals(currentPassword)) {
                        throw new RuntimeException("A senha atual está incorreta");
                    }
                        user.setPassword(newPassword);
                        return user;
                } )
                .orElseThrow( ()-> new RuntimeException("Usuario nao encontrado") );
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
}
