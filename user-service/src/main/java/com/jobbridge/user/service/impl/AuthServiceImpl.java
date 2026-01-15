package com.jobbridge.user.service.impl;

import com.jobbridge.user.config.KeycloakPropertiesConfig;
import com.jobbridge.user.dto.UserDTO;
import com.jobbridge.user.dto.request.LoginRequest;
import com.jobbridge.user.dto.request.RegisterRequest;
import com.jobbridge.user.dto.response.LoginResponse;
import com.jobbridge.user.entity.User;
import com.jobbridge.user.repository.UserRepository;
import com.jobbridge.user.service.AuthService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Keycloak keycloak;
    private final KeycloakPropertiesConfig propertiesConfig;
    private final UserRepository userRepository;

    private UsersResource usersResourceInstance() {
        return keycloak.realm(propertiesConfig.getRealm()).users();
    }

    @Override
    public UserDTO register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // ===== 1. Create user in Keycloak =====
        UserRepresentation user = new UserRepresentation();

        user.setUsername(request.getEmail());   // email làm username
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);

        user.setCredentials(List.of(credential));

        var response = keycloak.realm("test")
                .users()
                .create(user);

        String keycloakId = response.getLocation()
                .getPath()
                .replaceAll(".*/([^/]+)$", "$1");

        // ===== 2. Save to MySQL =====
        User newUser = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .keycloakId(keycloakId)
                .build();

         userRepository.save(newUser);
         return mapToUserDTO(user);
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


}
