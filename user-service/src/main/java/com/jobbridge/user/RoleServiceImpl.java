package com.jobbridge.user;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final Keycloak keycloak;
    private final KeycloakPropertiesConfig propertiesConfig;

    public RoleServiceImpl(Keycloak keycloak, KeycloakPropertiesConfig propertiesConfig) {
        this.keycloak = keycloak;
        this.propertiesConfig = propertiesConfig;
    }

    private RolesResource rolesResourceInstance() {
        return keycloak.realm(propertiesConfig.getRealm()).roles();
    }

    private RolesResource clientRolesResource(String clientId) {

        var clients = keycloak
                .realm(propertiesConfig.getRealm())
                .clients()
                .findByClientId(clientId);

        if (clients.isEmpty()) {
            throw new RuntimeException("Client not found: " + clientId);
        }

        String internalClientId = clients.get(0).getId();

        return keycloak
                .realm(propertiesConfig.getRealm())
                .clients()
                .get(internalClientId)
                .roles();
    }

    @Override
    public void create(RoleDto roleDto) {
//        RoleRepresentation role = new RoleRepresentation();
//        role.setName(roleDto.getName());
//        role.setDescription(roleDto.getDescription());
//
//        rolesResourceInstance().create(role);
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());

        clientRolesResource("test").create(role);
    }

    @Override
    public List<RoleDto> getAll() {
        var roleRepresentations = rolesResourceInstance().list();

        return roleRepresentations.stream().map(r -> new RoleDto(r.getName(), r.getDescription())).toList();
    }

    @Override
    public RoleDto getByName(String roleName) {
        var roleRepresentation = rolesResourceInstance().get(roleName).toRepresentation();

        return new RoleDto(roleRepresentation.getName(), roleRepresentation.getDescription());
    }
}
