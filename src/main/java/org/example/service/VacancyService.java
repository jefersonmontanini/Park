package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Vacancy;
import org.example.exception.CodeUniqueViolationException;
import org.example.exception.EntityNotFoundException;
import org.example.repository.VacancyRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    @Transactional
    public Vacancy save(Vacancy vacancy) {
        try {
            return vacancyRepository.save(vacancy);
        }catch (DataIntegrityViolationException exception) {
            throw new CodeUniqueViolationException(String.format("Vaga com codigo '%s' ja cadastrada", vacancy.getCode())
            );
        }
    }

    @Transactional(readOnly = true)
    public Vacancy getByCode(String code) {
        return vacancyRepository.findByCode(code).orElseThrow( ()-> new EntityNotFoundException(String.format("Vaga com codigo '%s' ja nao foi encontrada", code)));
    }

    @Transactional(readOnly = true)
    public Vacancy findByFreeVacancy() {
        return vacancyRepository.findFirstByStatus(Vacancy.StatusVacancy.FREE)
                .orElseThrow( ()-> new EntityNotFoundException("Nenhuma vaga livre foi encontrada") );
    }
}
