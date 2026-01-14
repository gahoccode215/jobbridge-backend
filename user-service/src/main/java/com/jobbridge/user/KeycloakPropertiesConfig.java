package com.jobbridge.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakPropertiesConfig {

    private String realm;

    private String endpoint;

    private String adminUsername;

    private String adminPassword;

    private final Application application = new Application();

    @Getter
    @Setter
    public static class Application {
        private String clientId;
        private String clientSecret;
    }
}
