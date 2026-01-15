package com.jobbridge.user.service.impl;

import com.jobbridge.user.config.KeycloakPropertiesConfig;
import com.jobbridge.user.dto.UserDTO;
import com.jobbridge.user.dto.request.LoginRequest;
import com.jobbridge.user.dto.request.RegisterRequest;
import com.jobbridge.user.dto.response.LoginResponse;
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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Keycloak keycloak;
    private final KeycloakPropertiesConfig propertiesConfig;
    private final RestTemplate restTemplate = new RestTemplate();

    private UsersResource usersResourceInstance() {
        return keycloak.realm(propertiesConfig.getRealm()).users();
    }

    @Override
    public UserDTO register(RegisterRequest registerRequest) {
        var userRepresentation = buildUserRepresentation(registerRequest);
         usersResourceInstance().create(userRepresentation);

        return mapToUserDTO(userRepresentation);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        String tokenUrl = propertiesConfig.getEndpoint()
                + "/realms/" + propertiesConfig.getRealm()
                + "/protocol/openid-connect/token";

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", "test");
        form.add("client_secret", propertiesConfig.getApplication().getClientSecret()); // nếu confidential
        form.add("username", request.getIdentifier());
        form.add("password", request.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> entity = new HttpEntity<>(form, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(tokenUrl, entity, Map.class);

        Map body = response.getBody();

        return LoginResponse.builder()
                .accessToken((String) body.get("access_token"))
                .refreshToken((String) body.get("refresh_token"))
                .expiresIn((Integer) body.get("expires_in"))
                .build();
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
