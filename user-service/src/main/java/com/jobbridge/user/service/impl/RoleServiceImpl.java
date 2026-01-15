package com.jobbridge.user.service.impl;

import com.jobbridge.user.config.KeycloakPropertiesConfig;
import com.jobbridge.user.dto.RoleDTO;
import com.jobbridge.user.service.RoleService;
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


    @Override
    public void createRole(RoleDTO roleDto) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        rolesResourceInstance().create(role);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        var roleRepresentations = rolesResourceInstance().list();
        return roleRepresentations.stream().map(r -> new RoleDTO(r.getName(), r.getDescription())).toList();
    }

}
