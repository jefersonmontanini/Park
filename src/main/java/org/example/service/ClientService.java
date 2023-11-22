package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.Client;
import org.example.exception.CpfUniqueViolationException;
import org.example.repository.ClientRepository;
import org.example.repository.projection.ClientProjection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    @Transactional(readOnly = true)
    public Client getById(Long id){
        return clientRepository.findById(id)
                .orElseThrow( ()-> new EntityNotFoundException(String.format("Cliente id=%s nao encontrado", id)) );
    }

    @Transactional(readOnly = true)
    public Page<ClientProjection> getAll(Pageable pageable) {
        return clientRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Client findByUserId(Long id) {
        return clientRepository.findByUserId(id);
    }
}
