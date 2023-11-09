package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.User;
import org.example.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository users;

    @Transactional
    public User save(User user) {
        return users.save(user);
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return users.findById(id)
                .orElseThrow( ()-> new RuntimeException("Usuario não encontrado") );
    }

    @Transactional
    public User updatePassword(long id, String password) {
        return users.findById(id)
                .map( user -> {
                    user.setPassword(password);
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
