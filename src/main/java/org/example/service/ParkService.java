package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.entity.Client;
import org.example.entity.ClientVacancy;
import org.example.entity.Vacancy;
import org.example.util.ParkUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkService {

    private final ClientVacancyService clientVacancyService;
    private final ClientService clientService;
    private final VacancyService vacancyService;


    @Transactional
    public ClientVacancy checkIn(ClientVacancy clientVacancy) {
        Client client = clientService.findByCpf(clientVacancy.getClient().getCpf());
        clientVacancy.setClient(client);

        Vacancy vacancy = vacancyService.findByFreeVacancy();
        vacancy.setStatus(Vacancy.StatusVacancy.OCCUPIED);
        clientVacancy.setVacancy(vacancy);

        clientVacancy.setEntryDate(LocalDateTime.now());

        clientVacancy.setReceipt(ParkUtils.generateReceipt());

        return clientVacancyService.save(clientVacancy);
    }

}
