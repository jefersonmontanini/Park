package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Client;
import org.example.exception.CpfUniqueViolationException;
import org.example.repository.ClientRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client save(Client client) {
        try {
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException exception) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' nao pode ser cadastrado pois ja existe no sistema", client.getCpf()));
        }
    }
}
