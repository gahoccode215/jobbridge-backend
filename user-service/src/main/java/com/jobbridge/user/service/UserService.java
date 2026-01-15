package com.jobbridge.user.service;

import com.jobbridge.user.dto.UserDTO;
import com.jobbridge.user.dto.response.UserProfileResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserProfileResponse getCurrentUser(Authentication authentication);
}
