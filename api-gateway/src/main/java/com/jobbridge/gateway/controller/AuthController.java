package com.jobbridge.gateway.controller;


import com.jobbridge.gateway.dto.LoginRequest;
import com.jobbridge.gateway.dto.LoginResponse;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String KEYCLOAK_URL =
            "http://localhost:9080/realms/test/protocol/openid-connect/token";
    private final String CLIENT_SECRET = "AVk9ezqmsWgMetklI0zXsDASuh3gwE0j";
    private final String CLIENT_ID = "test";

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();

        form.add("grant_type", "password");
        form.add("client_id", CLIENT_ID);
        form.add("client_secret", CLIENT_SECRET);
        form.add("username", request.getEmail());
        form.add("password", request.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> entity = new HttpEntity<>(form, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(KEYCLOAK_URL, entity, Map.class);

        Map body = response.getBody();

        return LoginResponse.builder()
                .accessToken((String) body.get("access_token"))
                .refreshToken((String) body.get("refresh_token"))
                .expiresIn((Integer) body.get("expires_in"))
                .build();
    }
}
