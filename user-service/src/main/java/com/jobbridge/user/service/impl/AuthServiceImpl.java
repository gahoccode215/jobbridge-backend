package com.jobbridge.user.service.impl;

import com.jobbridge.user.config.KeycloakPropertiesConfig;
import com.jobbridge.user.dto.UserDTO;
import com.jobbridge.user.dto.request.RegisterRequest;
import com.jobbridge.user.service.AuthService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Keycloak keycloak;
    private final KeycloakPropertiesConfig propertiesConfig;

    private UsersResource usersResourceInstance() {
        return keycloak.realm(propertiesConfig.getRealm()).users();
    }

    @Override
    public UserDTO register(RegisterRequest registerRequest) {
        var userRepresentation = buildUserRepresentation(registerRequest);
         usersResourceInstance().create(userRepresentation);

        return mapToUserDTO(userRepresentation);
    }

    private UserDTO mapToUserDTO(UserRepresentation userRepresentation) {
        return UserDTO.builder()
                .id(userRepresentation.getId())
                .username(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .build();
    }

    private UserRepresentation buildUserRepresentation(RegisterRequest registerRequest) {
        UserRepresentation userRepresentation  = new UserRepresentation();
        userRepresentation.setUsername(registerRequest.getUsername());
        userRepresentation.setCredentials(Collections.singletonList(buildCredentialRepresentation(registerRequest.getPassword())));
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(registerRequest.getEmail());
        userRepresentation.setFirstName(registerRequest.getFirstName());
        userRepresentation.setLastName(registerRequest.getLastName());
        userRepresentation.setEmailVerified(true);
        return userRepresentation ;
    }
    private CredentialRepresentation buildCredentialRepresentation(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }
}
