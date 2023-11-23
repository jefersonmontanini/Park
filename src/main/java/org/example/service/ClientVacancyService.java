package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.entity.ClientVacancy;
import org.example.repository.ClientVacancyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientVacancyService {

    private final ClientVacancyRepository repository;

    @Transactional
    public ClientVacancy save(ClientVacancy clientVacancy) {
        return repository.save(clientVacancy);
    }
}
