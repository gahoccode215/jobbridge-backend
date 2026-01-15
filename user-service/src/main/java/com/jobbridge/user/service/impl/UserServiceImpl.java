package com.jobbridge.user.service.impl;

import com.jobbridge.user.config.KeycloakPropertiesConfig;
import com.jobbridge.user.dto.UserDTO;
import com.jobbridge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;
    private final KeycloakPropertiesConfig propertiesConfig;

    private UsersResource usersResourceInstance() {
        return keycloak.realm(propertiesConfig.getRealm()).users();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return usersResourceInstance().list().stream().map(this::mapToUserDto).toList();
    }

    private UserDTO mapToUserDto(UserRepresentation userRepresentation) {
        return UserDTO.builder()
                .id(userRepresentation.getId())
                .username(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }
}
