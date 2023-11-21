package org.example.web.DTO.ResponseMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entity.Client;
import org.example.web.DTO.client.ClientCreateDTO;
import org.example.web.DTO.client.ClientResponseDTO;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDTO dto) {
        return new ModelMapper().map(dto, Client.class);
    }

    public static ClientResponseDTO toDTO(Client client) {
        return new ModelMapper().map(client, ClientResponseDTO.class);
    }
}
