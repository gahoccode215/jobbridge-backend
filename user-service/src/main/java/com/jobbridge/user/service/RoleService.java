package com.jobbridge.user.service;

import com.jobbridge.user.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    void createRole(RoleDTO roleDTO);
    List<RoleDTO> getAllRoles();
}
