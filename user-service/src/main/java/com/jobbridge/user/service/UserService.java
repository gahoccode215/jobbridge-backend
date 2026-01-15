package com.jobbridge.user.service;

import com.jobbridge.user.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
}
