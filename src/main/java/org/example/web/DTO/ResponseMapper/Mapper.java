package org.example.web.DTO.ResponseMapper;

import org.example.entity.User;
import org.example.web.DTO.UserResponseDTO;
import org.example.web.DTO.UserCreateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static User dtoToUser(UserCreateDTO createDTO) {
        return new ModelMapper().map(createDTO, User.class);
    }

    public static UserResponseDTO userToDto(User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDTO> props = new PropertyMap<User, UserResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(props);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> toListDto(List<User> users) {
        return users.stream().map(Mapper::userToDto).collect(Collectors.toList());
    }

}
