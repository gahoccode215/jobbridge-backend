package com.jobbridge.user.service;

import com.jobbridge.user.dto.UserDTO;
import com.jobbridge.user.dto.request.LoginRequest;
import com.jobbridge.user.dto.request.RegisterRequest;
import com.jobbridge.user.dto.response.LoginResponse;

public interface AuthService {
    UserDTO register(RegisterRequest registerRequest);
}
