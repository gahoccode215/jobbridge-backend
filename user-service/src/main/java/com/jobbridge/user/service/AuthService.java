package com.jobbridge.user.service;

import com.jobbridge.user.dto.UserDTO;
import com.jobbridge.user.dto.request.RegisterRequest;

public interface AuthService {
    UserDTO register(RegisterRequest registerRequest);
}
