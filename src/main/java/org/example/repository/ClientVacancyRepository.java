package org.example.repository;

import org.example.entity.ClientVacancy;
import org.example.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientVacancyRepository extends JpaRepository<ClientVacancy, Vacancy> {
}
