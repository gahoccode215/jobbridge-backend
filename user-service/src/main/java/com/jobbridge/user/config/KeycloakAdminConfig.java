package com.jobbridge.user.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class KeycloakAdminConfig {

    private final KeycloakPropertiesConfig propertiesConfig;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(propertiesConfig.getEndpoint())
                .realm("master")
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("admin-cli")
                .username(propertiesConfig.getAdminUsername())
                .password(propertiesConfig.getAdminPassword())
                .build();
    }
}
