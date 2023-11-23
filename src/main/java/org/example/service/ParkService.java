package org.example.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkService {

    private final ClientVacancyService service;
    private final ClientService clientService;
    private final VacancyService vacancyService;

}
