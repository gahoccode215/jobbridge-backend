package com.jobbridge.user.controller;

import com.jobbridge.user.dto.RoleDTO;
import com.jobbridge.user.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() {
        var roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody RoleDTO roleDto) {
        roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
